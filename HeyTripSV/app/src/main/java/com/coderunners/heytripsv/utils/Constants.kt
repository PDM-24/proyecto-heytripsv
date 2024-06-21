package com.coderunners.heytripsv.utils

import com.google.gson.annotations.SerializedName

object Constants {
    //api service
    const val BASE_URL = "http://192.168.1.12:3000"
    const val API_PATH ="/api"

    //Obtener post Home
    const val GET_TRIP_UPCOMING ="/post/upcoming/"
    const val GET_TRIP_RECENT ="/post/recent/"

    //Obtener post por agencia
    const val  GET_AGENCY = "/post/agency/"

    //Obtener post reportados
    const val   GET_REPORTED_POST= "/post/reported/"

    //Actualizar post
    const val POST_UPDATE = "/post/update/"
    const val POST_CREATE = "/post/create/"

    //Reportar Post
    const val PATCH_REPORT_POST = "/post/report/"

    //Eliminar post
    const val  DELETE_POST_AGENCY = "/post/own/"
    const val  DELETE_POST= "/post/"

    //Eliminar post reportado
    const val DELETE_REPORTED_POST  ="/post/undo-report/"

    //Registrar agencia
    const val POST_REGISTER_AGENCY = "/auth/register/agency/"
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
    const val POST_REGISTER_USER = "/auth/register/user/"
    const val POST_USER_NAME = "name"
    const val POST_USER_EMAIL = "email"
    const val POST_USER_SAVED = "saved"
    const val POST_USER_PASSWORD = "password"

    //Recuperar cuenta
    const val POST_RECOVER_PASSWORD = "/auth/recovery-code/"
    const val POST_CONFIRM_CODE = "/auth/confirm-code/"

    //Iniciar Sesion
    const val POST_LOGIN = "/auth/login/"

    //Retornar info usuario loggeado
    const val GET_INFO_AGENCY = "/auth/whoami/agency/"
    const val GET_INFO_USER = "/auth/whoami/user/"

    //Obtener agencias reportadas
    const val GET_REPORTED_AGENCY ="/agency/reported/"

    //Reportar agencia
    const val PATCH_REPORTED_AGENCY = "/agency/report/"

    //Eliminar agencia reportada
    const val DELETE_REPORTED_AGENCY = "/agency/undo-report"

    //Editar perfil
    const val  POST_EDIT_PROFILE = "/user/edit-profile/"

    //Guardar usuario
    const val GET_SAVED_USER = "/user/saved/"

    //Guardar post
    const val SAVE_POST = "/user/save/"

    //Api Response
    const val RESPONSE_SUCCESFUL = "result"
    const val RESPONSE_ERROR = "error"

}