package com.example.guimoye.mario.AcercaDe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.guimoye.mario.R;

import me.biubiubiu.justifytext.library.JustifyTextView;

/**
 * Created by Guimoye on 20/11/2016.
 */

public class frag_acercade extends Fragment {
    LayoutInflater inflater;
    ViewGroup container;
    JustifyTextView te;
    View v;


    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        this.inflater   =   inflater;
        this.container  =   container;
        v               =   inflater.inflate(R.layout.fragment_acercade,container,false);
  /*      te              =   (JustifyTextView) v.findViewById(R.id.text);

        te.setText("     Términos y condiciones de uso PARA LOS TAXISTAS Y LOS PASAJEROS. Este documento de términos de uso “Condiciones Generales” es aplicable para el uso de los servicios ofrecidos por Términos y condiciones de uso PARA LOS TAXISTAS Y LOS PASAJEROS.\n" +
                "\n" +
                "     Este documento de términos de uso “Condiciones Generales” es aplicable para el uso de los servicios ofrecidos por ContactTaxi. El usuario certifica que acepta todos los términos establecidos por ContactTaxi para acceder y requerir servicios. Si usted no está de acuerdo con los términos y condiciones del uso del sistema y las políticas de privacidad de ContactTaxi, no instale la aplicación, bórrela y/o no realice ningún uso de ello.\n" +
                "\n1. TERMINOS:" +
                "\n" +
                "(I) “Taxista(s)”: La persona tendrá la obligación de registrarse como taxista en la aplicación y aceptar su participación en el proyecto de ContactTaxi. \n" +
                "(Ii) “Pasajero(s)” significa cualquier usuario que se registra como pasajero en la aplicación; \n" +
                "(Iii) “Usuarios”: Taxistas y Pasajeros\n" +
                "\n" +

                "2. REGISTRO Y USO DEL SISTEMA:\n" +
                "a) Cuando se registra, el usuario acepta proveer información exacta, completa y actualizada que se requiera para completar el formulario, no teniendo ContactTaxi la obligación de supervisar o controlar la información.\n" +
                "b) Solo las personas que tienen la capacidad legal están autorizados a participar en el proyecto. Las personas que no cuenten con esta capacidad, entre ellos los menores de edad, deben ser asistidas por sus representantes legales. c) El TAXISTA acepta que para registrarse deberá pasar por una evaluación con el fin de ser aceptado en ContactTaxi, quien puede rehusarse o cancelar la cuenta del usuario en cualquier momento, ya sea por quejas o por políticas internas. d) ContactTaxi se reserva el derecho de usar cualquier acción legal posible para identificar a los usuarios, así como requerir, en cualquier momento, documentación extra que se considere apropiado para verificar la información personal del usuario.\n" +
                "c) ContactTaxi no se responsabiliza por cualquier daño como resultado de la pérdida o mal uso de la clave por parte de terceros. El usuario es el único responsable por ello.\n" +
                "d) No se puede transferir por ningún motivo, el registro del usuario a terceras personas.\n" +

                "\n3. LIMITACIONES DE LA RESPONSABILIDAD:" +
                "– Relación Taxistas-Pasajeros:\n" +
                "a) Al aceptar el servicio del TAXISTA, el PASAJERO reconoce que ContactTaxi no tiene una relación directa con el TAXISTA, solo facilita el contacto entre el taxista y el pasajero.\n" +
                "\n" +
                "     El usuario deberá tener el conocimiento y aceptar que ContactTaxi no se responsabiliza por ningún acto cometido por cualquier usuario, ya sea por robo, discusiones y otros, incluyendo el fin del compromiso a causa de algún acto, el registro de manera correcta asumido obligatoriamente por el usuario y pérdidas. El usuario tiene el conocimiento y aprueba que al registrarse y aceptar las políticas del servicio, debe cumplir y correr con los riesgos que este conlleva. ContactTaxi recomienda que toda transacción sea realizada de buena fe.\n" +
                "\n" +
                "– Disponibilidad e inconsistencia en el sistema:\n" +
                "a) ContactTaxi no garantiza que el sistema esté disponible sin interrupciones y que siempre esté libre de errores, y por tanto, no se responsabiliza por el daño causado a los usuarios.\n" +
                "b) ContactTaxi no se responsabiliza por cualquier error y/u inconsistencia de información con otros sistemas independientes, como el GPS, el radar y similares.\n" +
                "– Compensación por daños:\n" +
                "a) Los usuarios se comprometen a indemnizar a ContactTaxi y sus representantes de cualquier reclamo, demandas, pérdidas, responsabilidades, daños y costos, incluyendo los honorarios de los abogados y los costos judiciales que se incurran por el daño.\n" +

                "\n4. OBLIGACIONES, RESPONSABILIDADES Y RIEGOS DE PASAJEROS:\n" +
                "a) El PASAJERO certifica que está usando el servicio por su propia voluntad y reconoce y acepta las responsabilidades y riesgos por usar la aplicacion.\n" +
                "– Aprobación y cancelación de servicios:\n" +
                "a) La aceptación y cancelación del servicio puede ocurrir al principio por el TAXISTA, quien puede aceptar o negar el servicio cuando recibe la notificación del viaje. El PASAJERO puede cancelar la operación por cualquier motivo. Ambos reconocen que ContactTaxi no se responsabiliza por las demoras, cancelaciones de la operación y errores de comunicación entre el pasajero y el taxista, ni por cualquier retraso o incumplimiento en la entrega de servicios al conductor.\n" +
                "\n" +
                "– La información y riesgos del servicio:\n" +
                "a)El PASAJERO reconoce y declara que es consciente de todos los riesgos que implica el uso de la aplicacion y la contratación de servicios, comprometiéndose a tomar cuidado como es de esperar de alguien que elige un taxi para transportarse. \n" +
                "b) El PASAJERO reconoce y acepta que ContactTaxi no realiza ninguna selección de los TAXISTAS, simplemente el registro de ellos en el sistema, para participar no avala su idoneidad, la salud física o mental para prestar los Servicios. El pasajero reconoce que los datos proporcionados por el taxista, tales como: (i) el modelo del vehículo, (ii) la placa del vehículo, (iii) el nombre completo, (iv) foto de perfil, entre otros, fueron proporcionados por el propio conductor . Mediante la aceptación de los Servicios, el pasajero reconoce que ContactTaxi no tiene ninguna participación en la relación contractual entre el conductor y el pasajero.\n" +
                "\n" +
                "5. EVALUACION DE LOS USUARIOS:\n" +
                "a) Es la elección del pasajero de evaluar a los TAXISTAS que los transportan, asignando puntos y comentando sobre el servicio. Dicha evaluación será de uso exclusivo de ContactTaxi con la finalidad de brindar un mejor servicio al PASAJERO. ContactTaxi se reserva el derecho de usar las evaluaciones para mejorar el servicio, sea para uso interno o externo.\n" +
                "\n" +
                "6. POLITICA DE PRIVACIDAD:\n" +
                "a) ContactTaxi recaudará, almacenará y podrá transmitir o poner a disposición a un tercero, los datos y la información proporcionados por el pasajero en el registro en el sistema, no limitando la ubicación, el nombre completo, apellido, foto de perfil, teléfono y TAXISTA, además de los ya mencionados, el número de matrícula y los datos del vehículo, tales como la marca, modelo, color y placa, entre otros.\n" +
                "b) ContactTaxi implementa medidas de seguridad adecuadas para salvaguardar y ayudar a prevenir el acceso no autorizado, modificación, divulgación no autorizada o la retiro sin autorización de cualquier información recolectada.\n" +
                "\n" +
                "7. SANCIONES:\n" +
                "a) ContactTaxi podrá notificar, suspender o cancelar, temporal o permanentemente, la cuenta de cualquier usuario en cualquier momento, y tomar acciones legales si: (i) viola cualquiera de las representaciones, garantías y obligaciones contenidas en estas Condiciones de Uso o cualquier política o regla adyacente a la misma, (ii) las prácticas engañosas o fraudulentas, o (iii) ContactTaxi concluye que las actividades y actitudes han causado o pueden causar daño a los demás o al equipo de ContactTaxi. El usuario no tendrá derecho a ninguna indemnización o compensación por la cancelación o suspensión de su cuenta en el sistema.\n" +
                "\n" +
                "8. LICENCIA:\n" +
                "a) ContactTaxi, en conformidad y por acuerdo de los términos contenidos en este documento. ContactTaxi se reserva a todos los derechos sobre la aplicacion no expresamente concedidos aquí.\n" +
                "9. CONDICIONES GENERALES:\n" +
                "a) Los términos de uso no generan ninguna sociedad, franquicia o relación laboral entre el USUARIO y ContactTaxi.\n" +
                "b) Los términos pueden cambiar por ContactTaxi en cualquier momento. Los cambios serán obligatorios automáticamente.\n" +
                "c) Las presentes Condiciones de Uso se regirán e interpretarán de conformidad con la legislación de cada país y cualquier disputa que surja de este descargo no puede ser resuelto por las partes, puesto que será sometida a la jurisdicción de la ciudad cada determinado.\n" +
                "d) Al registrarse como conductor o como pasajero en el sistema y aceptar los términos, haciendo clic en la opción “Acepto los términos de uso”, el Usuario declara automáticamente y se compromete a cumplir con estos términos y todas las demás políticas y normas disponibles en el sistema.\n");
*/

        /*
        WebView view = (WebView) v.findViewById(R.id.textContent);
        String text;
        text = "<html><body><p align=\"justify\">";
        text+= "     Términos y condiciones de uso PARA LOS TAXISTAS Y LOS PASAJEROS. Este documento de términos de uso “Condiciones Generales” es aplicable para el uso de los servicios ofrecidos por Términos y condiciones de uso PARA LOS TAXISTAS Y LOS PASAJEROS.\n" +
                "\n" +
                "     Este documento de términos de uso “Condiciones Generales” es aplicable para el uso de los servicios ofrecidos por ContactTaxi. El usuario certifica que acepta todos los términos establecidos por ContactTaxi para acceder y requerir servicios. Si usted no está de acuerdo con los términos y condiciones del uso del sistema y las políticas de privacidad de ContactTaxi, no instale la aplicación, bórrela y/o no realice ningún uso de ello.\n" +
                "1. TERMINOS:\n" +
                "\n" +
                "(I) “Taxista(s)”: La persona tendrá la obligación de registrarse como taxista en la aplicación y aceptar su participación en el proyecto de ContactTaxi. \n" +
                "(Ii) “Pasajero(s)” significa cualquier usuario que se registra como pasajero en la aplicación; \n" +
                "(Iii) “Usuarios”: Taxistas y Pasajeros\n" +
                "\n" +
                "2. REGISTRO Y USO DEL SISTEMA:\n" +
                "a) Cuando se registra, el usuario acepta proveer información exacta, completa y actualizada que se requiera para completar el formulario, no teniendo ContactTaxi la obligación de supervisar o controlar la información.\n" +
                "b) Solo las personas que tienen la capacidad legal están autorizados a participar en el proyecto. Las personas que no cuenten con esta capacidad, entre ellos los menores de edad, deben ser asistidas por sus representantes legales. c) El TAXISTA acepta que para registrarse deberá pasar por una evaluación con el fin de ser aceptado en ContactTaxi, quien puede rehusarse o cancelar la cuenta del usuario en cualquier momento, ya sea por quejas o por políticas internas. d) ContactTaxi se reserva el derecho de usar cualquier acción legal posible para identificar a los usuarios, así como requerir, en cualquier momento, documentación extra que se considere apropiado para verificar la información personal del usuario.\n" +
                "c) ContactTaxi no se responsabiliza por cualquier daño como resultado de la pérdida o mal uso de la clave por parte de terceros. El usuario es el único responsable por ello.\n" +
                "d) No se puede transferir por ningún motivo, el registro del usuario a terceras personas.\n" +
                "3. LIMITACIONES DE LA RESPONSABILIDAD:\n" +
                "– Relación Taxistas-Pasajeros:\n" +
                "a) Al aceptar el servicio del TAXISTA, el PASAJERO reconoce que ContactTaxi no tiene una relación directa con el TAXISTA, solo facilita el contacto entre el taxista y el pasajero.\n" +
                "\n" +
                "     El usuario deberá tener el conocimiento y aceptar que ContactTaxi no se responsabiliza por ningún acto cometido por cualquier usuario, ya sea por robo, discusiones y otros, incluyendo el fin del compromiso a causa de algún acto, el registro de manera correcta asumido obligatoriamente por el usuario y pérdidas. El usuario tiene el conocimiento y aprueba que al registrarse y aceptar las políticas del servicio, debe cumplir y correr con los riesgos que este conlleva. ContactTaxi recomienda que toda transacción sea realizada de buena fe.\n" +
                "\n" +
                "– Disponibilidad e inconsistencia en el sistema:\n" +
                "a) ContactTaxi no garantiza que el sistema esté disponible sin interrupciones y que siempre esté libre de errores, y por tanto, no se responsabiliza por el daño causado a los usuarios.\n" +
                "b) ContactTaxi no se responsabiliza por cualquier error y/u inconsistencia de información con otros sistemas independientes, como el GPS, el radar y similares.\n" +
                "– Compensación por daños:\n" +
                "a) Los usuarios se comprometen a indemnizar a ContactTaxi y sus representantes de cualquier reclamo, demandas, pérdidas, responsabilidades, daños y costos, incluyendo los honorarios de los abogados y los costos judiciales que se incurran por el daño.\n" +
                "4. OBLIGACIONES, RESPONSABILIDADES Y RIEGOS DE PASAJEROS:\n" +
                "a) El PASAJERO certifica que está usando el servicio por su propia voluntad y reconoce y acepta las responsabilidades y riesgos por usar la aplicacion.\n" +
                "– Aprobación y cancelación de servicios:\n" +
                "a) La aceptación y cancelación del servicio puede ocurrir al principio por el TAXISTA, quien puede aceptar o negar el servicio cuando recibe la notificación del viaje. El PASAJERO puede cancelar la operación por cualquier motivo. Ambos reconocen que ContactTaxi no se responsabiliza por las demoras, cancelaciones de la operación y errores de comunicación entre el pasajero y el taxista, ni por cualquier retraso o incumplimiento en la entrega de servicios al conductor.\n" +
                "\n" +
                "– La información y riesgos del servicio:\n" +
                "a)El PASAJERO reconoce y declara que es consciente de todos los riesgos que implica el uso de la aplicacion y la contratación de servicios, comprometiéndose a tomar cuidado como es de esperar de alguien que elige un taxi para transportarse. \n" +
                "b) El PASAJERO reconoce y acepta que ContactTaxi no realiza ninguna selección de los TAXISTAS, simplemente el registro de ellos en el sistema, para participar no avala su idoneidad, la salud física o mental para prestar los Servicios. El pasajero reconoce que los datos proporcionados por el taxista, tales como: (i) el modelo del vehículo, (ii) la placa del vehículo, (iii) el nombre completo, (iv) foto de perfil, entre otros, fueron proporcionados por el propio conductor . Mediante la aceptación de los Servicios, el pasajero reconoce que ContactTaxi no tiene ninguna participación en la relación contractual entre el conductor y el pasajero.\n" +
                "\n" +
                "5. EVALUACION DE LOS USUARIOS:\n" +
                "a) Es la elección del pasajero de evaluar a los TAXISTAS que los transportan, asignando puntos y comentando sobre el servicio. Dicha evaluación será de uso exclusivo de ContactTaxi con la finalidad de brindar un mejor servicio al PASAJERO. ContactTaxi se reserva el derecho de usar las evaluaciones para mejorar el servicio, sea para uso interno o externo.\n" +
                "\n" +
                "6. POLITICA DE PRIVACIDAD:\n" +
                "a) ContactTaxi recaudará, almacenará y podrá transmitir o poner a disposición a un tercero, los datos y la información proporcionados por el pasajero en el registro en el sistema, no limitando la ubicación, el nombre completo, apellido, foto de perfil, teléfono y TAXISTA, además de los ya mencionados, el número de matrícula y los datos del vehículo, tales como la marca, modelo, color y placa, entre otros.\n" +
                "b) ContactTaxi implementa medidas de seguridad adecuadas para salvaguardar y ayudar a prevenir el acceso no autorizado, modificación, divulgación no autorizada o la retiro sin autorización de cualquier información recolectada.\n" +
                "\n" +
                "7. SANCIONES:\n" +
                "a) ContactTaxi podrá notificar, suspender o cancelar, temporal o permanentemente, la cuenta de cualquier usuario en cualquier momento, y tomar acciones legales si: (i) viola cualquiera de las representaciones, garantías y obligaciones contenidas en estas Condiciones de Uso o cualquier política o regla adyacente a la misma, (ii) las prácticas engañosas o fraudulentas, o (iii) ContactTaxi concluye que las actividades y actitudes han causado o pueden causar daño a los demás o al equipo de ContactTaxi. El usuario no tendrá derecho a ninguna indemnización o compensación por la cancelación o suspensión de su cuenta en el sistema.\n" +
                "\n" +
                "8. LICENCIA:\n" +
                "a) ContactTaxi, en conformidad y por acuerdo de los términos contenidos en este documento. ContactTaxi se reserva a todos los derechos sobre la aplicacion no expresamente concedidos aquí.\n" +
                "9. CONDICIONES GENERALES:\n" +
                "a) Los términos de uso no generan ninguna sociedad, franquicia o relación laboral entre el USUARIO y ContactTaxi.\n" +
                "b) Los términos pueden cambiar por ContactTaxi en cualquier momento. Los cambios serán obligatorios automáticamente.\n" +
                "c) Las presentes Condiciones de Uso se regirán e interpretarán de conformidad con la legislación de cada país y cualquier disputa que surja de este descargo no puede ser resuelto por las partes, puesto que será sometida a la jurisdicción de la ciudad cada determinado.\n" +
                "d) Al registrarse como conductor o como pasajero en el sistema y aceptar los términos, haciendo clic en la opción “Acepto los términos de uso”, el Usuario declara automáticamente y se compromete a cumplir con estos términos y todas las demás políticas y normas disponibles en el sistema.\n";
        text+= "</p></body></html>";
        view.loadData(text, "text/html", "utf-8");
        */

        return v;
    }


}
