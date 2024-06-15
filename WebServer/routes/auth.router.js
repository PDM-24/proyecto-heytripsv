const express = require('express');
const multer = require('multer'); 
const router = express.Router();
const storage = multer.memoryStorage(); // Store the file in memory
const upload = multer({ storage: storage});
const authController = require('../controllers/auth.controller');
const runValidation = require('../validators/index.middleware');
const { authentication } = require("../middlewares/auth.middlewares");
const { registerUserValidator, registerAgencyValidator, recoveryValidator, codeValidator } = require('../validators/auth.validators');

//Registrar agencia
router.post("/register/agency/", upload.single("image"), registerAgencyValidator, runValidation, authController.registerAgency);
//Registrar usuario
router.post("/register/user/", registerUserValidator, runValidation, authController.registerUser);

//Recuperación de contra
router.post("/recovery-code/", recoveryValidator, runValidation, authController.sendCode);
router.post("/confirm-code/", codeValidator, runValidation, authController.compareCode);

//Iniciar sesión
router.post("/login/", authController.login);

//Retorna la información del usuario loggeado
router.get("/whoami/agency/", authentication, authController.whoamiagency);   
router.get("/whoami/user/", authentication, authController.whoamiuser);    


module.exports = router;