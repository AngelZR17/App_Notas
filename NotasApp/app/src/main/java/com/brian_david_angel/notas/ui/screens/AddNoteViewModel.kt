package com.brian_david_angel.notas.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.brian_david_angel.notas.data.Item
import com.brian_david_angel.notas.data.ItemsRepository
import java.text.SimpleDateFormat
import java.util.Date

class AddNoteViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set



    fun updateUiState(itemDetails: ItemDetails) {
        itemUiState =
            ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun saveItem() {
        if (validateInput()) {
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean {
        return with(uiState) {
            titulo.isNotBlank() && contenido.isNotBlank()
        }
    }


}

fun fechaHoraActual() : String {
    val date = Date()
    val sdf = SimpleDateFormat("dd/MMM/yyyy hh:mm a")
    return sdf.format(date)
}

fun ItemDetails.toItem(): Item = Item(
    id = id,
    titulo = titulo,
    contenido = contenido,
    fecha=fechaHoraActual()
)

fun Item.toItemUiState(isEntryValid: Boolean = false): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    titulo = titulo,
    contenido = contenido,
    fecha=fecha
)

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val titulo: String = "",
    val contenido: String = "",
    val fecha:String=""
)