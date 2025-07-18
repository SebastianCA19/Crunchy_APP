package com.example.crunchy_app.productos.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity (tableName = "valores_atributo_producto", foreignKeys = {
        @ForeignKey(
                entity = AtributoProducto.class,
                parentColumns = "id_atributo_producto",
                childColumns = "id_atributo_producto",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        ),

        @ForeignKey(
                entity = Producto.class,
                parentColumns = "id_producto",
                childColumns = "id_producto",
                onDelete = ForeignKey.RESTRICT,
                onUpdate = ForeignKey.CASCADE
        )
},
        indices = {@Index(value = "id_atributo_producto")})


public class ValorAtributoProducto {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_valor_atributo_producto")
    private Integer idValorAtributoProducto;
    @ColumnInfo(name = "id_producto")
    @NonNull
    private Integer idProducto;;

    @ColumnInfo(name = "id_atributo_producto")
    @NonNull
    private Integer idAtributoProducto;

    @ColumnInfo(name= "valor_atributo_producto")
    @NonNull
    private float valorAtributoProducto;

    @ColumnInfo(name = "personalizado_id")
    private Integer personalizadoId;

    @NonNull
    @ColumnInfo(name = "activo")
    private boolean activo;


    public ValorAtributoProducto() {
    }

    public ValorAtributoProducto(Integer idProducto, Integer idAtributoProducto, float valorAtributoProducto) {
        this.idProducto = idProducto;
        this.idAtributoProducto = idAtributoProducto;
        this.valorAtributoProducto = valorAtributoProducto;
        this.activo = true;
    }


    public ValorAtributoProducto(Integer idProducto, Integer idAtributoProducto,float valorAtributoProducto, Integer personalizadoId) {
        this.valorAtributoProducto = valorAtributoProducto;
        this.idAtributoProducto = idAtributoProducto;
        this.idProducto = idProducto;
        this.personalizadoId = personalizadoId;
        this.activo = true;
    }


    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }


    public Integer getIdValorAtributoProducto() {
        return idValorAtributoProducto;
    }

    public Integer getIdAtributoProducto() {
        return idAtributoProducto;
    }

    public void setIdAtributoProducto(Integer idAtributoProducto) {
        this.idAtributoProducto = idAtributoProducto;
    }

    public float getValorAtributoProducto() {
        return valorAtributoProducto;
    }

    public void setValorAtributoProducto(float valorAtributoProducto) {
        this.valorAtributoProducto = valorAtributoProducto;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdValorAtributoProducto(Integer idValorAtributoProducto) {
        this.idValorAtributoProducto = idValorAtributoProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getPersonalizadoId() {
        return personalizadoId;
    }

    public void setPersonalizadoId(Integer personalizadoId) {
        this.personalizadoId = personalizadoId;
    }
}
