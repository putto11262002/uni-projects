const categoriesApi = "/api/categories"
const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
            categories: new Array()

        };
    }, computed: Vuex.mapState({
                customer: 'customer'
            }),
    methods: {
        getCategories(){
            axios.get(categoriesApi)
                .then(response => response.data)
                .then(data => this.categories = data)
                .catch(error => console.error(error))
        },
        viewProduct(category){
            window.location = `view-products.html?category=${category}`
        }

    },
    mounted(){
        if(this.customer){
            this.getCategories()
        }
    },

    // other modules
    mixins: [BasicAccessAuthentication]

});

// other component imports go here

import { BasicAccessAuthentication } from './authentication.js';

// import the navigation menu
import {navigationMenu} from "./navigation-menu.js"
// register the navigation menu under the <navmenu> tag
app.component('navmenu', navigationMenu);

// import data store
import { dataStore } from './data-store.js'
app.use(dataStore);

// mount the page - this needs to be the last line in the file
app.mount("body");