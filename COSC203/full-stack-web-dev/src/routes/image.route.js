const express = require("express")
const {uploadImageView, saveImage} = require("../controllers/image.controller");
const {validateBirdId} = require("../middlewares/upload.middleware");
const upload = require("../utils/upload")
const route = express.Router()

route.get("/", uploadImageView)
route.post("/:id", validateBirdId, upload.single("bird-image"), saveImage)

module.exports = route