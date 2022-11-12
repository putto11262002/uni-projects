const fs = require("fs")
const fsPromise = require("fs").promises
const path = require("path");
const config = require("../config")
const Bird = require("../models/bird.model")
const uploadImageView = async (req, res) => {
    const {id, photo} = await Bird.findById(req.query.id, "photo")
    res.render("upload-image", {id, photo})
}

const saveImage = async (req, res, next) => {

    if(!req.file){
        return res.status(400).redirect(`/image?id=${req.params.id}`)
    }

    const bird = await Bird.findById(req.params.id)
    // check if the image for the bird has been uploaded before
    if (bird.photo.source !== config.defaultImage) {
        // delete existing image
        await fsPromise.unlink(path.join(config.imageDirectory, bird.photo.source))
    }
    await Bird.updateOne({_id: req.params.id}, {$set: {'photo.source': req.file.filename, 'photo.credit': req.body.credit}})
    res.redirect("/")

}

module.exports = {
    uploadImageView,
    saveImage
}