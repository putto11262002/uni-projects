const customerApi = "/api/customers/"
const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
				username: new String(),
			password: new String(),
			error: null

        };
    },

    mounted() {
        // semicolon separated statements

     

    },

    methods: {
        // comma separated function declarations
		  signIn(){
			  this.createToken(this.username, this.password);
			  axios.get(customerApi + this.username)
				  .then(res => res.data)
				  .then(customer => {
					  delete customer.password
					  dataStore.commit("signIn", customer);
					  window.location = "index.html";
				  })
				  .catch(error => {
					  if (error.response.status === 401){
						  this.error = "Incorrect username/password"
						  return
					  }
					  console.error(error);

				  })
		  }
    },

    // other modules
    mixins: [BasicAccessAuthentication]

});

// other component imports go here

import { BasicAccessAuthentication } from './authentication.js';

// import data store
import { dataStore } from './data-store.js'
app.use(dataStore);

// import the navigation menu
import { navigationMenu } from './navigation-menu.js';

// register the navigation menu under the <navmenu> tag
app.component('navmenu', navigationMenu);

// mount the page - this needs to be the last line in the file
app.mount("body");