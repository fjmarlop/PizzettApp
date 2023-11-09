package es.fjmarlop.pizzettApp.dataBase.Remote.retrofit.models

data class LineaPedidoModel (
    val idLineaPedidoModel: Int,
    val producto: ProductoLineaPedidoModel,
    val cantidad: Int) {
}