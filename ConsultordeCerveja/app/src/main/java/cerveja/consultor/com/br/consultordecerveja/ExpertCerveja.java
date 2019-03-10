package cerveja.consultor.com.br.consultordecerveja;

import java.util.ArrayList;
import java.util.List;

public class ExpertCerveja{

    public List<String> getMarcas(String color){
        List<String> marcas = new ArrayList<String>();

        if (color.contains("Lager")){
            marcas.add("Coruja Otus Lager");
            marcas.add("Patagonia Amber");
            marcas.add("Sul Americana Extra");
        } else if(color.contains("Pilsen")){
            marcas.add("Pilsner Urquell");
            marcas.add("Bitburger");
            marcas.add("Brooklyn Pilsner");
            marcas.add("Belorizontina");
            marcas.add("Cauim");
        } else if(color.contains("Ale")){
            marcas.add("Baden Baden Chocolate");
            marcas.add("Kaiserdom Dark");
            marcas.add("Bierland Russian");
            marcas.add("BIerland Blumenau");
            marcas.add("Lohn Bier Laguna");
        } else if(color.contains("Bock")){
            marcas.add("Baden Baden Bock");
            marcas.add("Therezópolis Rubine");
            marcas.add("Saint Bier Bock");
            marcas.add("Tupiniquim Bock");
        } else if( color.contains("Weis")){
            marcas.add("Wolters Weiss");
            marcas.add("Patagonia Weisse");
            marcas.add("Dado Bier Weiss");
            marcas.add("Kaiserdom Hefe");
        }
        return marcas;
    }
}
