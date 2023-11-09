package es.fjmarlop.pizzettApp.dataBase.Remote.models

data class LineaPedidoModel (
    val idLineaPedidoModel: Int,
    val producto: ProductoLineaPedidoModel,
    val cantidad: Int) {
}