# 1 Introduction

I designed and implemented a genetic algorithm to optimise the performance of snakes
in a snake game. In this report, I will explain how I implemented the genetic algorithm
to optimise the neural network, which acts as the brain of the snakes. I will also discuss
my design decision and evaluate the performance of my agent.

# 2 Genetic algorithm

Genetic algorithm is derived from the process of natural selection in the real world.
Genetic algorithms use bio-inspired operations including mutation, crossover, and par-
ent selection to produce high-quality solutions to optimisation and search problems.
In genetic algorithm, a population of individuals is established. Each individual carries
chromosomes which is unique to the individual. The chromosome is used to determine
the fitness of each individual. Since individuals carry different chromosomes their fit-
ness is different in a given environment. To produce the next generation, individuals
are selected based on their fitness to crossover and reproduce offsprings which will
be in the next generation. The offspring produced can be mutated, this will intro-
duce new genes to the population. This process is repeated for a given number of
generations.

In this case, firstly, randomly generate a population of snakes. Since each snake is
randomly generated they carry different chromosomes, meaning their fitness will vary.
In this problem, the fitness of a snake is how well they perform in the game. To evaluate
the fitness of each snake, let the snake play a game. Then calculate the fitness of the
snakes based on how they perform in the game. The next generation will consist of
a proportion of individual in the current population who has high fitness (elites) and
offsprings reproduced from individuals in the current generation. Parents are selected
based on their fitness to crossover to reproduce offspring. Each offspring have a chance
of being mutated, the probability that the mutation occurs is called the mutation
rate. This process is repeated for some generations. This process aims to produce a
population that have better fitness than the previous ones, and eventually, obtain a
population of snakes that perform well in the game. There are various parameters,
such as mutation rate, elitism rate etc., and method of selecting parents, crossover and
mutation that can be used. In the following sections, I will discuss how I decide on
what method and parameter to use.

## 2.1 Chromosome

The actions of the snakes are controlled by a neural network. The neural network will
make decisions on what action to take given some input. All snakes have the same
neural network structure. However, the weight and the bias of the network in each


snake are different. The weight and bias will define how well a snake performs or its
fitness. Therefore, the weights and the biases can be considered as the chromosomes
of the snakes. In the neural network, the weights and biases are stored as a matrix and
1D vector respectively. However, in the genetic algorithm, the format of weight and
bias have to be transformed, so that chromosomes operations can easily be applied.
In the genetic algorithm, the chromosome is a tuple consisting of a list of wights and a
list of biases. In the initial population, the chromosome is initialised randomly. Each
weight is initialised to a random number between -1 and 1, and each bias is initialised
to a random number between 0 and 0.5.

## 2.2 Crossover

Crossover is the process of generating offspring’s chromosomes based on the parents’
chromosomes. This process aims to produce offspring that inherit the trait of both
parents but the offspring should not be identical to the parents. I have considered var-
ious crossover methods, including single-point, uniform, and multiple-point crossover.
In my case, the multiple-point crossover with 5 points gives the best performance of
the genetic algorithm. I implemented multiple-point crossover with 5 points, by re-
cursively carrying out single-point crossover 5 times. In single-point crossover, a point
on the parent chromosomes is picked randomly. The genes between the point are ex-
changed between the parents. This will produce two offspring; the first offspring will
have the genes from parent 1 before the crossover point and the genes from parent 2
after the point, and the second offspring will have the genes from parent 2 before the
crossover point and genes from parent 1 after the point.

## 2.3 Parent Selection

There are also a variety of parent selection methods available. Choosing the right
parent selection method is very important. Since if the method that tends to pick
parent with low fitness is used, the offspring is most likely to have low fitness. On the
other hand only picking individual with high fitness will reduce the variation in the
population. I ran the genetic algorithm with roulette wheel selection and tournament
selection. The roulette wheel selection yields a better performance, hence, is the
selected mutation method. In roulette wheel selection, the likelihood of selecting
an individual for breeding is correlated with that individual’s fitness; the higher the
fitness, the more likely it is that the individual will be selected. The process of selecting
individual can be thought of as a spinning a roulette wheel with as many pockets
as the population size, with the size of each pocket proportional to the individual’s
fitness.

## 2.4 Mutation

Mutation is a mean to introduce new gene to the population. This allows the popu-
lation to maintain diversity. The mutation methods that I have considered using are
random reset and swap mutation. After running the genentic algorithm with both
methods, I have chosen to use the random reset mutation as it introduces more vari-
ation to population, hence, better performance of the genetic algorithm. In random
reset, a random gene is selected from either the weights or bias region of the chromo-


some. Then replaced the selected gene with a randomly generated gene. If the selected
gene is in the weight region, the randomly generated gene will be between -1 and 1. If
the selected gene is in the bias region, the randomly generated gene will be between 0
and 0.5.

Mutation rate is a probability that the mutation occurs. To determine the optimal
value of the mutation rate, I ran the genetic algorithm with various mutation rate. It
is important that the mutation rate is not too high, because the genetic algorithm will
essentially become a random search. Whereas, low mutation rate will result in low
genetic diversity in the population. After running genetic algorithm with 0.05, 0.10,
0.20 and 0.30 mutation rate, I decided to use 0.05 mutation rate, as it yields the best
performance.

## 2.5 Elitism

Elitism refers to allowing the top n individual in the current population to be passed
on to the new population. This provides some guarantee that the average fitness of
the next generation will not decline significantly. I carried out some investigation to
determine the elitism rate, by running the genetic algorithm with 0.05, 0.10 and 0.
elitism rate. My investigation indicates that a 0.10 or 10% elitism rate provides the
best performance.

## 2.6 Fitness Function

The fitness function allows us to measure the performance of the snakes. I decided to
use the fitness function provided. The fitness function is given by:

![Fitness function equation](https://github.com/putto11262002/uni-projects/blob/master/COSC343/GA-NN-snake-game/mages/fitness-function.png)


# 3 Neutral network

The behaviour of a snake is controlled a neural network. The neural takes some input
and decides on what action to take: head left, forward or right.

## 3.1 Input features

The input of the neural network is derived from the percepts, which is an array of
metrics. Each matrix in the percepts array represents a frame within the game with
the head of the snake in the centre. For each frame, four features are extracted:

1. If there is any obstacle in the block adjacent to the left of the snake’s head. If
    there is an obstacle the value is 1, otherwise the value is 0.
2. If there is any obstacle in the block in front of the snake’s head. If there is an
    obstacle the value is 1, otherwise the value is 0.
3. If there is any obstacle in the block adjacent to the right of the snake’s head.
    If there is an obstacle the value is between the head of the snake to the nearest
    food in front of it.


4. The angle between the snake’s head and the nearest food in front of it. The
    angle can between -90 to 90 degrees. With 90 indicates that the food is direct
    to the left of its head, 0 degrees indicates that the food is directly in front of the
    snake, and -90 degrees indicates that the food is direct to the right of the snake.
    If there is no food in front of the snake the value is set to 0.

For the first 3 features, the block is considered to have an obstacle if it contains
body part of another snake or itself. The features are stored in a 1D vector. The
number of input features (can be though as the length of the input vector) depends
on the number of frames. The number of features is given by: numberOf Input=
4 ×numberOf F rames. Examples of how features are extract from the percepts is
shown below.

![Example of feature extraction for percepts with 2 frames.](https://github.com/putto11262002/uni-projects/blob/master/COSC343/GA-NN-snake-game/mages/featureExample1.png)

Example of feature extraction for percepts with 1 frame.

![Example of feature extraction for percepts with 2 frames.](https://github.com/putto11262002/uni-projects/blob/master/COSC343/GA-NN-snake-game/mages/featureExmaple2.png)

Example of feature extraction for percepts with 2 frames.

I am using one frame percepts as the default configuration, as it yields the best per-
formance. Multiple frames percepts can introduce more complexity and can result in
overfitting. The default configuration of percepts’ field of view is 9. A larger field
of view increases the probability of the snake finding food. Hence, improving the
performance.

## 3.2 Design

The input layer of the neural network hasnumberOf F rames×4 nodes. The number
of nodes corresponds to the features extract from the percepts. The neural network has
single hidden layer with 15 nodes. All the nodes in the hidden layer are activated by the
ratified linear function (ReLU). The output layer has three nodes corresponding the
three actions: head left, forward, right. The nodes in the output layer are activated
by softmax function, to obtain the probability corresponding to each actions. The
action with the highest probability is selected. I have chosen to use only one hidden
layer as, in this instance, a single hidden layer captures all the aspects of the problem.
Also, Using multiple hidden layers can result in overfitting the data. The weights of
the neural network is initialised to a random number between -1 and 1. The bias is
initialised to a random number between 0.0 and 0.5.


# 4 GA performance

![Performance plot](https://github.com/putto11262002/uni-projects/blob/master/COSC343/GA-NN-snake-game/mages/ga-performance.png)

In Figure 3, I trained my agent for 500 generations against a random agent, to evaluate
the performance of my genetic algorithm. The plot shows that the average fitness score
increase over generations, showing that the neural network is adapting to the game.
The average fitness increase significantly from 3 to 7 between the 1st generation to the
50th generation. Then the average fitness become stagnant at around 7 after the 50th
generation. This indicates that the maximum average fitness the genetic algorithm can
achieve is around 7 and only around 50 generations is required to achieve this.

# 5 Agent performance againt the randon agent

To evaluate the performance of my agent, I trained the agent for 50 generations and
let it play against a random agent for 5 games. Table 1, shows that my agent won
every game and score on average 158.2 per game.

# 6 Conclusion

In conclusion, the genetic algorithm is able to train the neural network, which is
initialised with random weight and bias, to win a snake game against the random agent. 
I find that the most challenging aspect of this assignment is to extract useful
feature from the percepts. Tuning the parameter of the genetic algorithm, has also
been very challenging. As I have to experiment with a wide range of parameters and
combinations of parameters.
