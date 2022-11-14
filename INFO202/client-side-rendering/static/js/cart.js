
const salesApi = "/api/sales"
import {NumberFormatter, Sale} from "./utils.js"
const app = Vue.createApp({

    data() {
        return {

        };
    },

    computed: {
        subTotal: function (){
            let subTotal = 0;
            return this.items.map(item => subTotal += (item.quantityPurchased * item.salePrice))
        },
        ...Vuex.mapState({
        product: 'selectedProduct',
        items: 'items',
        customer: 'customer',

    })},


    mounted() {
        // semicolon separated statements
        if (!this.customer) {
            window.location = "sign-in.html"
            return
        }


    },

    methods: {
        // comma separated function declarations
        checkOut(){
            if(!this.items) return

            let sale = new Sale(this.customer, this.items)

            axios.post(salesApi, sale)
                .then(response => response.data)
                .then(data =>  {
                    dataStore.commit("clearItems")
                    window.location = "order-confirmation.html"
                })
                .catch(err => {
                    console.error(err)
                })

        }

    },
    mixins: [NumberFormatter, BasicAccessAuthentication]

});

/* other component imports go here */

import { BasicAccessAuthentication } from './authentication.js';

import {navigationMenu} from "./navigation-menu.js"
app.component("navmenu", navigationMenu)

// import data store
import { dataStore } from './data-store.js'
app.use(dataStore);

// mount the page - this needs to be the last line in the file
app.mount("body");