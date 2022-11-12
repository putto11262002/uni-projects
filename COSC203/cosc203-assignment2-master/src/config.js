require("dotenv").config()

module.exports =  {
    port: process.env.PORT,
    mongo_uri: process.env.MONGO_URI,
    mongooseOptions: {

    },
    imageDirectory: "public/images",
    defaultImage: "default-image.jpeg"

}