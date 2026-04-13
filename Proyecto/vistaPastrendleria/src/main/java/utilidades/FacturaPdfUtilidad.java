package utilidades;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import dtos.PedidoDto;
import dtos.DetallePedidoDto;

import java.io.OutputStream;

public class FacturaPdfUtilidad {

    public static void generarFactura(PedidoDto pedido, OutputStream outputStream) {

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font normal = new Font(Font.FontFamily.HELVETICA, 12);

            document.add(new Paragraph("FACTURA", titulo));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Pedido Nº: " + pedido.getIdPedido(), normal));
            document.add(new Paragraph("Fecha: " + pedido.getFechaPedido(), normal));
            document.add(new Paragraph("Dirección: " + pedido.getDireccionPedido(), normal));
            document.add(new Paragraph("Estado: " + pedido.getEstadoPedido(), normal));
            document.add(Chunk.NEWLINE);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{3, 1, 1, 1});

            tabla.addCell("Producto");
            tabla.addCell("Cantidad");
            tabla.addCell("Precio");
            tabla.addCell("Subtotal");

            if (pedido.getDetalles() != null) {
                for (DetallePedidoDto d : pedido.getDetalles()) {
                    tabla.addCell(d.getNombreProducto());
                    tabla.addCell(String.valueOf(d.getCantidad()));
                    tabla.addCell(d.getPrecioUnitario() + " €");
                    tabla.addCell(d.getSubtotal() + " €");
                }
            }

            document.add(tabla);
            document.add(Chunk.NEWLINE);

            Font totalFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            document.add(new Paragraph("TOTAL: " + pedido.getTotalPedido() + " €", totalFont));

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
