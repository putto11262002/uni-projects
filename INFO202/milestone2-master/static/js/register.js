const customerApi = "/api/register"
const app = Vue.createApp({

    data() {
        return {
            // models map (comma separated key/value pairs)
				customer: new Object(),
				errors: null
        };
    },

    mounted() {
        // semicolon separated statements
    },

    methods: {
        // comma separated function declarations
		  register(){
			  axios.post(customerApi, this.customer)
				  .then(() => {
					  window.location = "sign-in.html";
				  })
					  .catch(error => {
						  if (error.response.status === 400){
							  this.errors = error.response.data.messages
							  return
						  }
						  console.error(error)

					  })
		  }
    },

    // other modules
    mixins: []
});

// other component imports go here
// import the navigation menu
import { navigationMenu } from './navigation-menu.js';

// register the navigation menu under the <navmenu> tag
app.component('navmenu', navigationMenu);

// import data store
import { dataStore } from './data-store.js'
app.use(dataStore);


// mount the page - this needs to be the last line in the file
app.mount("body");