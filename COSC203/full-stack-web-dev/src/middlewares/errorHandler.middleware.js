
module.exports = (err, req, res, next) => {
    console.error(err)
    return res.status(500).render("500")
}