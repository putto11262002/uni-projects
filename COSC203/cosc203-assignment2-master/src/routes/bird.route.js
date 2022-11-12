const express = require("express")
const {createBird, createBirdView, homeView, viewBirdView, deleteBird, updateBird, updateBirdView} = require("../controllers/bird.controller");
const router = express.Router()

router.get("/", homeView)

router.get("/view-bird/:id", viewBirdView)

router.get("/create-bird", createBirdView)

router.get("/update-bird/:id", updateBirdView)

router.post("/api/bird/create", createBird)

router.get("/api/bird/:id/delete", deleteBird)

router.post("/api/bird/:id/update", updateBird)

module.exports = router