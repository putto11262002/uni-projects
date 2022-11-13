const mongoose = require("mongoose")
const {Schema, model} = mongoose
const config = require("../config")
const path = require("path");
const {string_normalize, status_values} = require("../utils/bird");
const birdSchema = new Schema({
    primary_name: {type: String, required: ["Primary name cannot be empty"]},
    english_name: {type: String, required: [true, "English name cannot be empty"]},
    order: {type: String, required: [true, "Order cannot be empty"]},
    family: {type: String, required: [true, "Family cannot be empty"]},
    scientific_name: {type: String, required: [true, "Scientific name cannot be empty"]},
    status: {type: String, required: [true, "Status cannot be empty"]},
    status_value: Number,
    photo: {
        credit: {type: String, default: "No credit"},
        source: {type: String, default: config.defaultImage}
    },
    size: {
        length: {
            value: Number,
            units: {type: String, default: "cm"}
        },
        weight: {
            value: Number,
            units: {type: String, default: "g"}
        }
    },
    normalized: {
        primary_name: String,
        english_name: String,
        order: String,
        family: String,
        scientific_name: String,
        other_names: String
    }

})



birdSchema.pre('save', function() {
    this.normalized = {
        primary_name: string_normalize(this.primary_name),
        english_name: string_normalize(this.english_name),
        order: string_normalize(this.order),
        family: string_normalize(this.family),
        scientific_name: string_normalize(this.scientific_name),
    }

    this.status_values = status_values[this.status] === undefined ? -1 : status_values[this.status]
});

birdSchema.pre("updateOne", function (){
    if(this.getUpdate().primary_name & this.getUpdate().english_name & this.getUpdate().order & this.getUpdate().family & this.getUpdate().scientific_name){
        this.getUpdate().normalized = {
            primary_name: string_normalize(this.getUpdate().primary_name),
            english_name: string_normalize(this.getUpdate().english_name),
            order: string_normalize(this.getUpdate().order),
            family: string_normalize(this.getUpdate().family),
            scientific_name: string_normalize(this.getUpdate().scientific_name),
        }
    }

    if(this.getUpdate().status){
        this.getUpdate().status_values = status_values[this.getUpdate().status] === undefined ? -1 : status_values[this.getUpdate().status]
    }


})

module.exports = model("Bird", birdSchema)
