package com.galvezssr.agendos.ui.main

import androidx.lifecycle.*
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.kernel.DbFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(usuarioActual: String) : ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var listaContactos: Flow<List<Contact>>

    // Recycler del Fragment
    private val _listaResultante = MutableLiveData<Flow<List<Contact>>>()

    ////////////////////////////////////////////////////
    // INCIALIZADOR ////////////////////////////////////
    ////////////////////////////////////////////////////

    init {

        /** Iniciamos el ViewModel. Su comportamiento es que los datos realmente seran modificados aqui,
         * mientras que el Fragment se encarga de escuchar cuando los datos se han modificado, para asi
         * aplicar los nuevos valores en el XML **/
        viewModelScope.launch {

            /** Obtenemos la lista de contactos de la BBDD segun el usuario con el que estemos logueados **/
            listaContactos = DbFirestore.getFlow(usuarioActual)
            _listaResultante.postValue(listaContactos)
        }
    }


    ////////////////////////////////////////////////////
    // GETTERS /////////////////////////////////////////
    ////////////////////////////////////////////////////

    fun getListaContactos(): LiveData<Flow<List<Contact>>> = _listaResultante
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val usuarioActual: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(usuarioActual) as T
    }
}
