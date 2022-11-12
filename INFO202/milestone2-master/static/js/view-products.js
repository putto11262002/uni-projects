const productsApi = "/api/products"
const categoriesApi = "/api/categories"
const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            products: new Array(),
            categories: new Set()

        };
    },

    computed: Vuex.mapState({
        customer: "customer"
    }),

    async mounted() {
        // semicolon separated statements
        if (!this.customer) {
            window.location = "sign-in.html"
            return
        }
        this.getCategories()
        let category = this.getCategoryFromQueryParam()
        if (category === "All"){
            this.getProducts()
        }else{
            this.filterProductsByCategory(category)
        }


    },

    methods: {
        // comma separated function declarations
        getProducts(){
            axios.get(productsApi)
                .then(response => response.data)
                .then(data => this.products = data)
                .catch(error => {
                    console.error(error);
                });
        },
        getCategoryFromQueryParam(){
            const urlParams = new URLSearchParams(window.location.search);
            let category = urlParams.get("category")
            if (category === "All" | category === null) return "All"
                return category
        }
        ,
        async getCategories(){
           axios.get(categoriesApi)
               .then(response => response.data)
               .then(data => this.categories = data)
               .catch(error => console.error(error))
        },
        filterProductsByCategory(category){
            axios.get(categoriesApi + "/" + category)
                .then(response => response.data)
                .then(data => this.products = data)
                .catch(error => console.error(error))

        },
        buyProduct(product){
            dataStore.commit("selectProduct", product)
            window.location = "buy-product.html"
        },
        isValidCategory(category){
            return this.categories.has(category)
        }

    },

    // other modules
    mixins: [NumberFormatter, BasicAccessAuthentication]

});

// other component imports go here
import {NumberFormatter} from "./utils.js";

import { BasicAccessAuthentication } from './authentication.js';

// import data store
import { dataStore } from './data-store.js'
app.use(dataStore);

import {navigationMenu} from "./navigation-menu.js"
app.component("navmenu", navigationMenu)
// mount the page - this needs to be the last line in the file
app.mount("body");