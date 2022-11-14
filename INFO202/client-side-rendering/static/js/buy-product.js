const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            quantity: 1,
            pageStatus: "loading",
            error: null

        };
    },

    computed: Vuex.mapState({
        product: 'selectedProduct',
        customer: "customer"
    }),
    mounted() {
        if (!this.customer) {
            window.location = "sign-in.html"
            return
        }
        if(this.product !== null){
            this.pageStatus = "ready"
        }
    },
    watch: {
        product(prevProduct, newProduct){
            if (newProduct !== null){
                this.pageStatus = "ready"
            }
        }

    },

    methods: {
        // comma separated function declarations
        addToCart(){
            if (this.quantity < 1) {
                this.error = "Invalid quantity"
                return
            }
            if (this.quantity > this.product.quantityInStock) {
                this.error = "Insufficient quantity in stock."
                return
            }
            dataStore.commit("addItem", new SaleItem(this.product, this.quantity));
            this.pageStatus = "successful"
        }

    },

    // other modules
    mixins: [NumberFormatter]

});

// other component imports go here

// import the navigation menu
import {NumberFormatter, SaleItem} from "./utils.js"

import { navigationMenu } from './navigation-menu.js';
// register the navigation menu under the <navmenu> tag
app.component('navmenu', navigationMenu);

// import data store
import { dataStore } from './data-store.js'
app.use(dataStore);

// mount the page - this needs to be the last line in the file
app.mount("body");