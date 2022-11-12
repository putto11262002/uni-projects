export const navigationMenu = {

    computed: {
        ...Vuex.mapState({
            signedInCustomer: "customer"
        })
    },
    data(){
        return {
            currentPage: window.location.pathname.substring(1).slice(0, -5)
        }
    },


    template:
        `
   <nav>
   <p class="text-center">Tech Center</p>
    
    <ul>
        <li :class="{'current-link': currentPage === 'index' | currentPage === ''}"><a href="index.html">Home</a></li>

        <li  :class="{'current-link': currentPage === 'sign-in'}" v-if="!signedInCustomer"><a href="sign-in.html">Sign In</a></li>
           
       <li  :class="{'current-link': currentPage === 'view-products'}" v-if="signedInCustomer"><a href="view-products.html">Browse Products</a></li>
        <li  :class="{'current-link': currentPage === 'cart'}" v-if="signedInCustomer"><a href="cart.html">View Cart</a></li>
        <li  v-if="signedInCustomer" @click.prevent="signOut()"><a href="sign-out">Sign Out</a></li>
        
     
    </ul>
    
    <p class="text-center" v-if="signedInCustomer">Welcome {{signedInCustomer.firstName}} </p>
    
     
</nav>
    `,

    methods: {
        signOut() {
            sessionStorage.clear();
            window.location = "index.html"

        }
    }
};