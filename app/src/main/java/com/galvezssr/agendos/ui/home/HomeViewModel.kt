package com.galvezssr.agendos.ui.home

import androidx.lifecycle.*
import com.galvezssr.agendos.kernel.Contact
import com.galvezssr.agendos.kernel.DbFirestore
import kotlinx.coroutines.launch

class HomeViewModel(usuarioActual: String) : ViewModel() {

    ////////////////////////////////////////////////////
    // VARIABLES ///////////////////////////////////////
    ////////////////////////////////////////////////////

    private lateinit var listaContactos: List<Contact>

    // Recycler del Fragment
    private val _listaResultante = MutableLiveData<List<Contact>>(emptyList())


    ////////////////////////////////////////////////////
    // INCIALIZADOR ////////////////////////////////////
    ////////////////////////////////////////////////////

    init {

        /** Iniciamos el ViewModel. Su comportamiento es que los datos realmente seran modificados aqui,
         * mientras que el Fragment se encarga de escuchar cuando los datos se han modificado, para asi
         * aplicar los nuevos valores en el XML **/
        viewModelScope.launch {

            /** Obtenemos la lista de contactos de la BBDD segun el usuario con el que estemos logueados **/
            listaContactos = DbFirestore.getContactsFromUser(usuarioActual)
            _listaResultante.postValue(listaContactos)
        }
    }

    ////////////////////////////////////////////////////
    // GETTERS /////////////////////////////////////////
    ////////////////////////////////////////////////////

    fun getListaResultante(): LiveData<List<Contact>> = _listaResultante
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val usuarioActual: String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(usuarioActual) as T
    }
}
