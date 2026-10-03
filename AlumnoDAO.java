import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class AlumnoDAO {
    private final Path archivo;

    public AlumnoDAO() {
        archivo = Paths.get("alumnos.txt");
        try {
            if (!Files.exists(archivo)) {
                Files.createFile(archivo);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public boolean registrar(Alumno alumno) throws IOException {
        if (buscarPorId(alumno.getId()) != null) {
            return false; 
        }
        String registro = alumno.toString() + System.lineSeparator();
        Files.writeString(archivo, registro, StandardOpenOption.APPEND);
        return true;
    }

    public List<String> obtenerTodos() throws IOException {
        return Files.readAllLines(archivo);
    }

    public String buscarPorId(String id) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);
        String prefijo = id + " - ";
        for (String linea : lineas) {
            if (linea.startsWith(prefijo)) {
                return linea;
            }
        }
        return null;
    }

    public boolean actualizar(String id, String nuevoNombre) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);
        boolean actualizado = false;
        String prefijo = id + " - ";
        
        for (int i = 0; i < lineas.size(); i++) {
            if (lineas.get(i).startsWith(prefijo)) {
                lineas.set(i, id + " - " + nuevoNombre);
                actualizado = true;
                break;
            }
        }
        
        if (actualizado) {
            Files.write(archivo, lineas);
        }
        return actualizado;
    }

    public boolean eliminar(String id) throws IOException {
        List<String> lineas = Files.readAllLines(archivo);
        String prefijo = id + " - ";
        boolean eliminado = lineas.removeIf(linea -> linea.startsWith(prefijo));
        
        if (eliminado) {
            Files.write(archivo, lineas);
        }
        return eliminado;
    }
}
