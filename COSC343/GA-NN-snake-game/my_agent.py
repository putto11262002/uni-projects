__author__ = "Put Suthisrisinlpa"
__organization__ = "COSC343/AIML402, University of Otago"
__email__ = "sutpu703@student.otago.ac.nz"

import math
import random
import numpy as np
import matplotlib.pyplot as plt

# interactive mode for plotting
plt.ion()

agentName = "<put_agent>"
perceptFieldOfVision = 9  # Choose either 3,5,7 or 9
perceptFrames = 1  # Choose either 1,2,3 or 4
trainingSchedule = [("random", 150)]

class Snake:
    """
    This is the class for your snake/agent
    """
    def __init__(self, nPercepts, actions):
        # You should initialise self.chromosome member variable here (whatever you choose it
        # to be - a list/vector/matrix of numbers - and initialise it with some random
        # values)
        self.fitness = None
        self.nPercepts = nPercepts
        self.actions = actions
        network = Network()
        network.add(ExtracFeature())
        network.add(DenseLayer(input_size=4 * perceptFrames, output_size=15, act="relu"))
        network.add(DenseLayer(input_size=15, output_size=3, act="softmax"))

        self.chromosomes = network

    def AgentFunction(self, percepts):

        predicted_action = self.chromosomes.predict(percepts)
        selected_action_idx = 0
        for i, score in enumerate(predicted_action):
            if score > predicted_action[selected_action_idx]:
                selected_action_idx = i
        return self.actions[selected_action_idx]


class GA:
    """
    This class contain all the GA related operations
    """

    def __init__(self, fitness_func, plot_fitness=False, mutation="no_mutation", crossover="uniform",
                 mutation_rate=0.02,
                 elitism=0.05, parent_selection="roulette", n_crossover=2, k=10, mutate_elite=False):
        """
        :param fitness_func: The fitness function used in the GA
        :param plot_fitness: if True, plot average fitness against generation. Default to False
        :param mutation: The type of mutation used in GA
        :param crossover: The type of crossover used
        :param mutation_rate: The rate of mutation as probability
        :param elitism: The proportion of the fittest individuals that will be in the next population
        :param parent_selection: The parent selection method used
        :param n_crossover: If multiple point crossover is used, this specifies how many times the crossover occurs. Default to 2
        :param k: The number of individual that will e picked, if tournament selection is used. Default to 10
        :param mutate_elite: True, pass elite individual through the mutation function
        """
        self.k = k
        self.mutate_elite = mutate_elite
        self.n_crossover = n_crossover
        self.mutation = mutation
        self.mutation_rate = mutation_rate
        self.elitism = elitism
        self.parent_selection = parent_selection
        self.fitness_func = fitness_func
        self.plot_fitness = plot_fitness
        self.crossover = crossover
        self.scores = []
        self.generations = []

        self.population = None
        mutation_funcs = {
            "swap": self.swap_mutation,
            "random_reset": self.random_resetting_mutation,
            "no_mutation": self.no_mutation
        }
        selected_mutation_func = mutation_funcs.get(mutation)
        if selected_mutation_func is None:
            raise Exception("Invalid mutation argument")
        self.mutation_func = selected_mutation_func

        crossover_funcs = {
            "uniform": self.uniform_crossover,
            "single_point": self.single_point_crossover,
            "multiple_point": self.multi_point_crossover
        }

        selected_crossover_func = crossover_funcs.get(crossover)
        if selected_crossover_func is None:
            raise Exception("Invalid crossover argument")
        self.crossover_func = selected_crossover_func

        parent_selection_funcs = {
            "roulette": self.roulette_wheel_selection,
            "tournament": self.tournament_selection
        }
        selected_parent_selection_func = parent_selection_funcs.get(parent_selection)
        if selected_parent_selection_func is None:
            raise Exception("Invalid parent selection argument")
        self.parent_selection_func = selected_parent_selection_func

    def evolve(self, old_population):
        """
        :param old_population: The current population of snakes
        :return: A tuple consisting of:
                    - a list of the new_population of snakes that is of the same length as the old_population,
                    - the average fitness of the old population
        """

        N = len(old_population)

        # Calculate the number of elite individuals that will be in the next generation
        elite_num = round(N * self.elitism)

        nPercepts = old_population[0].nPercepts
        actions = old_population[0].actions

        # Assign fitness score to the corresponding snake
        fitness = self.fitness_func(old_population)
        for snake, score in zip(old_population, fitness):
            snake.fitness = score

        # Sort the population base on fitness in descending order
        old_population = sorted(old_population, key=lambda x: x.fitness, reverse=True)

        # Create new population list
        new_population = list()

        # Select elite individual to the next generation
        new_population.extend(old_population[0:elite_num])

        # if mutate_elite is set to True
        # each elite individual will be passed through the mutation function.
        # Each individual has self.mutation_rate chance of being mutated
        if self.mutate_elite:
            for i in range(0, len(new_population)):
                snake = new_population[i]
                mutated_chromosome = self.mutation_func(snake.chromosomes.weights_bias_vector())
                snake.chromosomes.set_weight_by_vector(mutated_chromosome)
                new_population[i] = snake

        # Create N - elite_num number of new snakes
        # Each loop two parents will be selected based on the parent selection criteria
        # the parents will cross over and produce two children
        # each child is passed through the selected mutation function, then add to the new population
        for n in range(elite_num, N, 2):
            # Create a new snakes
            snake1 = Snake(nPercepts, actions)
            snake2 = Snake(nPercepts, actions)

            # Select parents
            parent1, parent2 = self.parent_selection_func(old_population)

            # Extract parents' chromosome (weights and bias)
            parent1_chromosome, parent2_chromosome = parent1.chromosomes.weights_bias_vector(), parent2.chromosomes.weights_bias_vector()

            # Crossover parennts to produce two childrens
            child1, child2 = self.crossover_func(parent1_chromosome, parent2_chromosome)

            # Pass each child through mutation (self.mutation_rate chance of being mutated)
            child1, child2 = self.mutation_func(child1), self.mutation_func(child2)

            # Set the chromosome of the new snakes
            snake1.chromosomes.set_weight_by_vector(child1)
            snake2.chromosomes.set_weight_by_vector(child2)

            # Add the new snakes to the new population
            new_population.append(snake1)
            new_population.append(snake2)

        avg_fitness = np.mean(fitness)

        # store the average fitness of each generation
        self.scores.append(avg_fitness)

        # plot average fitness against generation if self.plot_fitness if True
        if self.plot_fitness:
            plt.plot(range(len(self.scores)), self.scores)
            plt.suptitle("Average fitness over generations")
            plt.ylabel("Fitness")
            plt.xlabel("Generation")
            plt.show()

        return (new_population, avg_fitness)

    def multi_point_crossover(self, parent1, parent2):
        """
        In multiple point crossover, single point is perform n times
        :param parent1: An instance of Snake
        :param parent2: An instance of Snake
        :return: A tuple consisting of two child Snake
        """
        for i in range(self.n_crossover):
            parent1, parent2 = self.single_point_crossover(parent1, parent2)
        return parent1, parent2

    def uniform_crossover(self, parent1, parent2):
        """
        In uniform crossover, for each gene in the chromosome
        a random number is generated between 0 and 1
        if the random number is equal or greater than 0.5 the child will inherit the gene from parent1
        else it will inherit the gene from parent2
        :param parent1: An instance of Snake
        :param parent2: An instance of Snake
        :return: A tuple consisting of two child Snake
        """
        child_weights, child_bias = [], []
        parent1_weights, parent1_bias = parent1
        parent2_weights, parent2_bias = parent2

        for gene1, gene2 in zip(parent1_weights, parent2_weights):
            if random.random() >= 0.5:
                child_weights.append(gene1)
            else:
                child_weights.append(gene2)

        for gene1, gene2 in zip(parent1_bias, parent2_bias):
            if random.random() <= 0.5:
                child_bias.append(gene1)
            else:
                child_bias.append(gene2)

        return (child_weights, child_bias)

    def single_point_crossover(self, parent1, parent2):
        """
        In single point crossover the crossover index is randomly generated
        The index determines the point of exchange between the chromosome of the parents
        :param parent1: An instance of Snake
        :param parent2: An instance of Snake
        :return: A tuple consisting of two child Snake
        """

        parent1_weights, parent1_bias = parent1
        parent2_weights, parent2_bias = parent2

        weights_cross_idx = random.randint(0, len(parent1_weights) - 1)
        bias_cross_idx = random.randint(0, len(parent1_bias) - 1)

        child1 = (np.append(parent1_weights[:weights_cross_idx], parent2_weights[weights_cross_idx:]),
                  np.append(parent1_bias[:bias_cross_idx], parent2_bias[bias_cross_idx:]))
        child2 = (np.append(parent2_weights[:weights_cross_idx], parent1_weights[weights_cross_idx:]),
                  np.append(parent2_bias[:bias_cross_idx], parent1_bias[bias_cross_idx:]))

        return (child1, child2)

    def random_resetting_mutation(self, chromosome):
        """
        In random reset, randomly pick a gene index (can be in weights in bias)
        then replace the gene with a randomly generated gene
        If the gene picked is in the weight region the randomly generated gene will be between -1 and 1
        if the gene picked is in the bias region the randomly generated gene will be between 0 and 0.5

        """

        if random.random() >= self.mutation_rate:
            return chromosome
        weights, bias = chromosome
        idx = random.randint(0, len(weights) + len(bias) - 2)
        if idx < len(weights):
            gene = random.uniform(-1, 1)
            weights[idx] = gene
        else:
            gene = random.uniform(0, 0.5)
            bias[idx - len(weights)] = gene

        return (weights, bias)

    def swap_mutation(self, chromosome):
        """
        In swap mutation, two random gene is selected and swap positions
        The swap mutation is done independently for the weights and bias region
        """

        if random.random() >= self.mutation_rate:
            return chromosome
        # Swap in weights region
        weights, bias = chromosome
        idx1 = random.randint(0, len(weights) - 1)
        idx2 = random.randint(0, len(weights) - 1)

        tempIdx1 = weights[idx1]
        weights[idx1] = weights[idx2]
        weights[idx2] = tempIdx1


        if random.random() >= self.mutation_rate:
            return weights, bias
        # swap in bias region
        idx1 = random.randint(0, len(bias) - 1)
        idx2 = random.randint(0, len(bias) - 1)

        tempIdx1 = bias[idx1]
        bias[idx1] = bias[idx2]
        bias[idx2] = tempIdx1

        return weights, bias

    def no_mutation(self, chromosome):
        """
        used when in no_mutation mode
        """
        return chromosome

    def roulette_wheel_selection(self, pop):
        """
        Each snake take up a space in a probability space proportional to their fitness
        randomly select two parent base on the probability space. This will result in
        snakes with higher fitness being more likely to get selected
        """

        fitness_sum = sum([snake.fitness for snake in pop])

        chrom_probs = [snake.fitness / fitness_sum for snake in pop]

        return tuple(np.random.choice(pop, p=chrom_probs, size=2, replace=False))

    def tournament_selection(self, pop):
        """
        Select k individual from the population randomly
        Select the top two individual as the parents
        """
        selected = np.random.choice(pop, size=self.k, replace=False)
        selected = sorted(selected, key=lambda x: x.fitness, reverse=False)
        return selected[0], selected[1]


class Network:
    """
    A Class represent a neural network
    """

    def __init__(self):
        self.layers = []

    def add(self, layer):
        """
        Add layer to the network
        :param layer: An instance of Layer, Tranform
        """
        self.layers.append(layer)

    def predict(self, x):
        output = x
        for layer in self.layers:
            output = layer.forward_propagate(output)
        return output

    def weights_bias(self):
        """
        :return: A tuple consisting of:
                    - A list of weight of each layer as a input_size * output_size matrix
                    - A list of bias of each layer as a 1D vector
        """
        weights = []
        bias = []

        for layer in self.layers:
            if isinstance(layer, ExtracFeature):
                continue
            weights.append(layer.weights)
            bias.append(layer.bias)
        return (weights, bias)

    def weights_bias_vector(self):
        """
        :return: a tuple of consisting of:
                - A 1D vector of all the weights in each layer of the network
                - A 1D vector of the bias in each layer of the network
        """
        weights = []
        bias = []
        for layer in self.layers:
            if isinstance(layer, ExtracFeature):
                continue
            weights.extend(np.asarray(layer.weights).flatten())
            bias.extend(np.asarray(layer.bias).flatten())
        return (weights, bias)

    def set_weight_by_vector(self, chromosome):
        """
        Set the weight of each layer in the network by a chromosome
        :param chromosome: a tuple of consisting of:
                            - A 1D vector of all the weights in each layer of the network
                            - A 1D vector of the bias in each layer of the network
        """
        chrom_weights, chrom_bias = chromosome
        start_idx = 0
        for layer in self.layers:
            if isinstance(layer, ExtracFeature):
                continue
            weight_length = layer.input_size * layer.output_size
            weights = chrom_weights[start_idx: weight_length + start_idx]
            layer.weights = np.reshape(weights, (layer.input_size, layer.output_size))
            start_idx += weight_length

        start_idx = 0
        for layer in self.layers:
            if isinstance(layer, ExtracFeature):
                continue
            bias_length = layer.output_size
            bias = chrom_bias[start_idx:bias_length + start_idx]

            layer.bias = np.array(bias)
            start_idx += bias_length


class DenseLayer:
    """
    Class represents a dense layer in a neural network
    The randomly generated weights are numbers between -1 and 1
    The randomly generated bias are number between 0 and 5
    """

    def __init__(self, input_size, output_size, act=None):
        """
        :param input_size: The size of 1D vector input
        :param output_size: The sie of the 1D vector output. It can also be though as the number of nodes in the layer
        :param act: The activation function. Default to None
        """

        self.input_size = input_size
        self.output_size = output_size
        self.output = None
        self.input = None
        self.act = act
        self.weights = np.random.uniform(-1, 1, (input_size, output_size))
        self.bias = np.random.uniform(0, 0.5, (1, output_size))

    def forward_propagate(self, input):
        """
        Taking an input from the previous layer
        Sum the weighted input and add the bias. Then pass it through an activation function that was selected
        and return the result as the output
        :param input: input as a 1D vector
        :return: output as a 1D vector
        """
        self.input = input
        self.output = self.activation(np.asarray(np.dot(self.input, self.weights) + self.bias).flatten())
        return self.output

    def activation(self, x):
        """
        :param x: The sum of the weighted input plus bias in a 1D vector format
        :return: input that has been pass through the selected activation function.
        If no activation function is selected, the input is returned
        """
        activations = {
            "sigmoid": self.sigmoid,
            "relu": self.relu,
            "softmax": self.softmax,
            None: None
        }

        selected_act = activations[self.act]
        if selected_act is None:
            return x
        else:
            return selected_act(x)

    def sigmoid(self, x):
        return 1 / (1 + np.exp(-x))

    def relu(self, x):
        return np.maximum(0, x)

    def softmax(self, x):
        e_x = np.exp(x - np.max(x))
        return e_x / e_x.sum(axis=0)


class ExtracFeature:
    """
    An input layer of the network that extract features from the input (list of frames) For each frame the layer
    extract the following 4 features: - 1 if there is an obstacle to the left of the agent, otherwise 0 - 1 if there
    is an obstacle in front of the agent, otherwise 0 - 1 if there is an obstacle to the left of the agent,
    othwise 0 - The agent angle of the snake to the food, The angle can between -90 to 90 degrees. With -90
    indicating that the food is directly to the right of its head, 0 degree inciting that the food is directly in
    front of the snake, and 90 degrees indicating that the food is directly to the right of the snake. If no food is found, value is default to 0
    """

    def __init__(self):
        self.input = None
        self.output = None

    def forward_propagate(self, input):
        self.input = input
        self.output = []

        for frame in self.input:
            self.output.extend(self.extract(frame))
        return self.output

    def extract(self, frame):
        output = []
        # check if the possible moves
        # 0 - free, 1 can't go

        front = (math.floor(len(frame) / 2) - 1, math.floor(len(frame[0]) / 2))
        left = (math.floor(len(frame) / 2), math.floor(len(frame[0]) / 2) - 1)
        right = (math.floor(len(frame) / 2), math.floor(len(frame[0]) / 2) + 1)

        # check if the neighbours are unoccupied
        output.append(self.check_obstable(left, frame))
        output.append(self.check_obstable(front, frame))
        output.append(self.check_obstable(right, frame))
        output.append(self.angle_to_nearest_food(frame))
        return output

    def angle_to_nearest_food(self, frame):
        """
        Find the nearest food in the top half of the frame using breath-first search
        then calculate the angle between the snake and the food
        """
        # Get the coordinate of the snake head, treat it as the source node
        src = (math.floor(len(frame) / 2), math.floor(len(frame[0]) / 2))
        visited = Set()
        q = Queue()
        dest = None  # the destination which will store the coordinate of the nearest food

        q.add(src)
        visited.add(src)

        while not q.isEmpty():
            current = q.remove()
            cr, cc = current

            if frame[cr][cc] == 2:
                dest = current
                break;

            neighbours = []

            # if the coordinates of neighbours are valid
            if self.validCorrdinate((cr - 1, cc), frame):
                neighbours.append((cr - 1, cc))
            if self.validCorrdinate((cr + 1, cc), frame):
                neighbours.append((cr + 1, cc))
            if self.validCorrdinate((cr, cc - 1), frame):
                neighbours.append((cr, cc - 1))
            if self.validCorrdinate((cr, cc + 1), frame):
                neighbours.append((cr, cc + 1))

            # Add each neighbour to the queue is haven't been explored
            for neighbour in neighbours:
                if not visited.contains(neighbour):
                    q.add(neighbour)
                    visited.add(neighbour)

        # if not food found return 0
        if dest is None:
            return 0
        else:
            return math.atan2(src[1] - dest[1], src[0] - dest[0]) * (180 / math.pi)

    def validCorrdinate(self, coordinate, frame):
        r, c = coordinate
        return 0 <= r <= math.floor(len(frame) / 2) and 0 <= c < len(frame[0])

    def check_obstable(self, coordinate, frame):
        """
        Given a coordinate check if there is any obstacle in that position
        if so return 1, otherwise 0
        Obstacle includes other snakes and its body
        """
        res = 0
        neighbour = frame[coordinate[0]][coordinate[1]]
        if neighbour == -1:
            res = 1
        if neighbour == 1:
            res = 1
        return res


class Set:
    """
    A class representing a Set
    """

    def __init__(self):
        self.set = {}

    def add(self, coordinate):
        r, c = coordinate
        self.set[f'{r},{c}'] = coordinate

    def contains(self, coordinate):
        r, c = coordinate
        return self.set.get(f'{r},{c}') != None


class Queue():
    """
    A class representing a Queue
    """

    def __init__(self):
        self.list = []

    def add(self, i):
        self.list.append(i)

    def remove(self):
        return self.list.pop(0)

    def size(self):
        return len(self.list)

    def isEmpty(self):
        return self.size() == 0


def evalFitness(population):
    N = len(population)

    # Fitness initializer for all agents
    fitness = np.zeros((N))

    # This loop iterates over your agents in the old population - the purpose of this boiler plate
    # code is to demonstrate how to fetch information from the old_population in order
    # to score fitness of each agent
    for n, snake in enumerate(population):
        # snake is an instance of Snake class that you implemented above, therefore you can access any attributes
        # (such as `self.chromosome').  Additionally, the object has the following attributes provided by the
        # game engine:
        #
        # snake.size - list of snake sizes over the game turns
        # .
        # .
        # .
        maxSize = np.max(snake.sizes)
        turnsAlive = np.sum(snake.sizes > 0)
        maxTurns = len(snake.sizes)

        # This fitness functions considers snake size plus the fraction of turns the snake
        # lasted for.  It should be a reasonable fitness function, though you're free
        # to augment it with information from other stats as well

        fitness[n] = maxSize + turnsAlive / maxTurns
    return fitness


ga = GA(plot_fitness=True, fitness_func=evalFitness, mutation="random_reset", mutation_rate=0.05, crossover="multiple_point",
            elitism=0.1,
            n_crossover=5)

def newGeneration(old_population):
        """
        Generate new generation
        :param old_population: The previous population
        :return: a tuple consisting of:
                    - a list of the new_population of snakes that is of the same length as the old_population,
                    - the average fitness of the old population
        """
        new_population, avg_fitness = ga.evolve(old_population)
        return (new_population, avg_fitness)
