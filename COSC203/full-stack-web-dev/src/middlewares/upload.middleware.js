const Bird = require("../models/bird.model")
const upload = require("../utils/upload")
const multer = require("multer")
const validateBirdId = async (req, res, next) => {
    const {id} = req.params
    const bird = await Bird.findById(id)
    if(!bird) return res.redirect("/")
    next()
}



module.exports = {
    validateBirdId,

}