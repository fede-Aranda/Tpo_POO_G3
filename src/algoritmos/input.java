package algoritmos;
import java.util.Scanner;

//_________________________________________________________________________________
//input no es una clase fundamental para la consigna del TP. Es solo un archivo
//que traigo de implementaciones de codigos anteriores que ayuda a facilitar
//la prueba por consola de los metodos. Sanitizando la entrada de datos del usuario
// Se utiliza como una libreria.
//_________________________________________________________________________________

//antes de usar las funciones, primero instanciar la clase de la sig manera:
//    input input = new input()
//    input.inicializar();
//al finalizar el codigo recordar llamar al metodo cerrar() para cerrar el scanner.

public class input{
    Scanner entrada;

    public void inicializar(){
        entrada = new Scanner(System.in);
    }
    
    // abre una entrada por consola al usuario esperando que ingrese un int
    // maneja las excepciones en caso de que lo que ingrese el usuario != int 
    public int numero(){  
        int num = 0;
        boolean error = true;
            while(error){
                try {
                    System.out.print("numero: ");
                    num = entrada.nextInt();
                    error = false;
                }
                catch(Exception e) {
                    System.out.println("El caracter ingresado no es un numero, vuelva a intentarlo");
                    entrada.next();
                    error = true;
                }
            }
        entrada.nextLine();
        return num;
    }

    public String string(){  
        String string;
        string = entrada.nextLine();
        while(string == "\n"){
            if(string==""){ //no permite strings vacios
                System.out.println("No se ha ingresado nada, ingrese caracteres antes de continuar: ");
            }
            string = entrada.nextLine();
        }
        return string;
    }

    public boolean yesOrNo (){
        String string;
        System.out.print("ingrese su respuesta (y/n): ");
        string = entrada.nextLine();
        System.out.println();
        while(!string.equals("n") && !string.equals("N") && !string.equals("y") && !string.equals("Y") ){
            System.out.println("Debe ingresar una respuesta válida (y/n): ");
            string = entrada.nextLine();
        }
        return (string.equals("y") || string.equals("Y"));
    }

//al finalizar el codigo recordar llamar al metodo cerrar() para cerrar el scanner.
    public void cerrar(){
        entrada.close();
    }
}
