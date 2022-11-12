const express = require("express")
const path = require("path")

const errorHandler = require("./src/middlewares/errorHandler.middleware")
const dbConnect = require("./src/utils/dbConnect")
const notFoundController = require("./src/middlewares/notFound.middleware")
const config = require("./src/config")
const imageRouter = require("./src/routes/image.route")
const birdRouter = require("./src/routes/bird.route")
const {urlencoded} = require("express");
const app = express()

/**
 * Middlewares
 */

app.use(urlencoded({extended: true}))
// serving static assets e.g. css
app.use(express.static("public"))
app.use("/bootstrap", express.static(path.join(__dirname, "node_modules/bootstrap/dist")))
app.use("/bootstrap-icons", express.static(path.join(__dirname, "node_modules/bootstrap-icons/font")))
app.set("views", path.join(__dirname, "src/views"))
app.set("view engine", "pug")


/**
 * Routes
 */

app.use("/image", imageRouter)
app.use("/", birdRouter)

// handle page not found
app.use(errorHandler)
app.use("*", notFoundController)

dbConnect()

app.listen(config.port, () => console.log(`Server running on port ${config.port}`))