const Bird = require("../models/bird.model")
const { buildAggregationPipeline, } = require("../utils/bird");
const {promises: fsPromise} = require("fs");
const path = require("path");
const config = require("../config");


const createBirdView = (req, res) => {
    res.render("create-bird", {bird: {size: {length: {}, weight: {}}}, form_action: "/api/bird/create", method: "POST"})
}

const homeView = async (req, res, next) => {
    try{
        const {search, status, sort} = req.query
        const pipeline = buildAggregationPipeline(search, sort, status)
        const birds = await Bird.aggregate(pipeline)
        res.render("home", {birds, includeFilterForm: true})
    }catch (err){
        next(err)
    }
}

const viewBirdView = async (req, res, next) => {
    try{
        const {id} = req.params
        const bird = await Bird.findById(id)
        if(!bird){
            return res.redirect("/")
        }
        res.render("view-bird", {bird})
    }catch (err){
        next(err)
    }
}

const updateBirdView = async (req, res) => {
    try{
        const {id} = req.params
        const bird = await Bird.findById(id)
        if(!bird){
            return res.redirect("/")
        }
        res.render("update-bird", {bird, form_action: `/api/bird/${bird._id}/update`, method: "POST"})
    }catch (err){
        next(err)
    }
}

const createBird = async (req, res, next) => {
    try{
        const bird = new Bird(req.body)
        const createdBird = await bird.save()
        res.status(201).redirect(`/image?id=${createdBird._id}`)
    }catch (err){
        if(err.name === "ValidationError"){
            // TODO -- error messages
            return res.render("create-bird", {bird: req.body, form_action: "/api/bird/create", method: "POST"})
        }
        next(err)
    }
}

const deleteBird = async (req, res, next) => {
    try{
        const {id} = req.params
        const bird = await Bird.findById(id)
        if (!bird){
            return res.redirect("/")
        }
        if(bird.photo.source !== config.defaultImage){
            await fsPromise.unlink(path.join(config.imageDirectory, bird.photo.source))
        }
        await Bird.deleteOne({_id: id})
        res.redirect("/")
    }catch (err){
        next(err)
    }
}

const updateBird = async (req, res, next) => {
    try{
        const {id} = req.params
        await Bird.updateOne({_id: id}, req.body,  { runValidators: true })
        res.redirect(`/image?id=${id}`)
    }catch (err){
        if(err.name === "ValidationError"){
            // TODO -- error messages
            return res.render("update-bird", {bird: req.body, form_action: `/api/bird/${req.params.id}/update`, method: "POST"})
        }
        next(err)
    }
}

module.exports = {
    createBirdView,
    createBird,
    homeView,
    viewBirdView,
    deleteBird,
    updateBird,
    updateBirdView
}