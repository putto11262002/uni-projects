const status = require("http-status")

module.exports = (req, res) => {
    return res.status(status.NOT_FOUND).render("404")
}