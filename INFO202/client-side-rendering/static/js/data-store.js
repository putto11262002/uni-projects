export const dataStore = Vuex.createStore({

    state() {
        // signed in customer
        customer: null;
        
        // the shopping cart items
        items: new Array();

        selectedProduct: null;

        authToken: null;
    },

    mutations: {

        // user signs in
        signIn(state, customer) {
            state.customer = customer;
            state.items = new Array();
        },
        selectProduct(state, product) {
            state.selectedProduct = product;
        },
        // add item to cart
        addItem(state, item) {
            if (!state.items) {
                state.items = new Array()
            }
            state.items.push(item);
        },
        // clear cart items
        clearItems(state) {
            state.items = new Array();
        },
        // store basic access token
        authToken(state, token) {
            state.authToken = token;
        }


    },

    // add session storage persistence
    plugins: [window.createPersistedState({storage: window.sessionStorage})]

});