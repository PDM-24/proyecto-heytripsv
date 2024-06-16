const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller')
const { authentication } = require("../middlewares/auth.middlewares");
const runValidation = require('../validators/index.middleware');

router.post("/edit-profile/", authentication, runValidation, userController.editOwn);

module.exports = router;