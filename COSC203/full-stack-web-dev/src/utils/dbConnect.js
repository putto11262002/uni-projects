const mongoose = require('mongoose');
const config = require("../config")
module.exports = async () => {
    try {
        await mongoose.connect(config.mongo_uri, config.mongooseOptions)
        console.log("Connected to database")
    } catch (error) {
        console.error(error)
    }
}