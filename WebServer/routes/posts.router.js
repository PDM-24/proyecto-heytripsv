const express = require('express');
const multer = require('multer'); 
const router = express.Router();
const storage = multer.memoryStorage(); // Store the file in memory
const upload = multer({ storage: storage});
const postController = require('../controllers/post.controller');
const runValidation = require('../validators/index.middleware');
const { authentication, authorization } = require("../middlewares/auth.middlewares");
const { savePostValidator, validateCategory, validateId, validateFilter, validateStatus, reportValidator, validateInfo } = require('../validators/post.validators');

//Crear/actualizar post
router.post(["/create/", "/update/:id"], upload.single("image"), authentication, savePostValidator, runValidation, postController.savePost);
router.get('/agency/:id', validateId, runValidation, postController.findFromAgency);

//Eliminar post
router.delete('/own/:id', authentication, validateId, runValidation, postController.deleteOwn);
router.delete('/:id', authentication, authorization, validateId, runValidation, postController.deletePost);


//Obtener posts para home
router.get('/upcoming/', postController.findUpcoming)
router.get('/recent/', postController.findRecent)

module.exports = router