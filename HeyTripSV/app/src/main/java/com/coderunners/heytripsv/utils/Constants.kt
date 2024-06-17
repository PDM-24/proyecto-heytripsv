package com.coderunners.heytripsv.utils

import com.google.gson.annotations.SerializedName

object Constants {
    //api service
    const val BASE_URL = ""
    const val API_PATH ="/api"

    //Obtener post Home
    const val GET_TRIP_UPCOMING ="/upcoming/"
    const val GET_TRIP_RECENT ="/recent/"

    //Obtener post por agencia
    const val  GET_AGENCY = "/agency/:id"

    //Obtener post reportados
    const val   GET_REPORTED_POST= "/reported/"

    //Actualizar post
    const val POST_UPDATE = "/update/:id"
    const val POST_CREATE = "/create/"

    //Reportar Post
    const val PATCH_REPORT_POST = "/report/:id"

    //Eliminar post
    const val  DELETE_POST = "/own/:id"
    const val  DELETE_POST_ID= "/:id"

    //Registrar agencia
    const val POST_REGISTER_AGENCY = "/register/agency/"
    const val POST_NAME = "name"
    const val POST_EMAIL= "email"
    const val POST_DUI= "dui"
    const val POST_DESCRIPTION= "description"
    const val POST_NUMBER = "number"
    const val POST_INSTAGRAM ="instagram"
    const val POST_FACEBOOK ="facebook"
    //const val POST_IMAGE= "image"
    const val POST_PASSWORD ="password"

    //Registrar usuario
    const val POST_REGISTER_USER = "/register/user/"
    const val POST_USER_NAME = "name"
    const val POST_USER_EMAIL = "email"
    const val POST_USER_SAVED = "saved"
    const val POST_USER_PASSWORD = "password"

    //Recuperar cuenta
    const val POST_RECOVER_PASSWORD = "/recovery-code/"
    const val POST_CONFIRM_CODE = "/confirm-code/"

    //Iniciar Sesion
    const val POST_LOGIN = "/login/"

    //Retornar info usuario loggeado
    const val GET_INFO_AGENCY = "/whoami/agency/"
    const val GET_INFO_USER = "/whoami/user/"

    //Obtener agencias reportadas
    const val GET_REPORTED_AGENCY ="/reported/"

    //Reportar agencia
    const val PATCH_REPORTED_AGENCY = "report/:id"

    //Editar perfil
    const val  POST_EDIT_PROFILE = "/edit-profile/"

    //Guardar usuario
    const val GET_SAVED_USER = "/saved/"

    //Api Response
    const val RESPONSE_SUCCESFUL = "result"
    const val RESPONSE_ERROR = "error"

}