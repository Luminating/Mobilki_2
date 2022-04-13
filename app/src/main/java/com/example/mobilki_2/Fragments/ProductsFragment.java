package com.example.mobilki_2.Fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobilki_2.Catalog;
import com.example.mobilki_2.Product;
import com.example.mobilki_2.ProductAdapter;
import com.example.mobilki_2.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //private static final String SITE_URL = "http://www.socket.by";
    private static final String SITE_URL = "https://www.socket.by";
    private HashMap<String, String> categoryMap = new HashMap<>();
    //private static final String CATEGORY_1 = "/kompyutery/komplektuyushchie-dlya-pk/operativnaya-pamyat/";
    //private static final String CATEGORY_2 = "/kompyutery/komplektuyushchie-dlya-pk/protsessory/";

    private View screenView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        categoryMap.put("Processor","/kompyutery/komplektuyushchie-dlya-pk/protsessory/");
        categoryMap.put("Memory","/kompyutery/komplektuyushchie-dlya-pk/operativnaya-pamyat/");
        categoryMap.put("Motherboard","/kompyutery/komplektuyushchie-dlya-pk/materinskie-platy/");
        isInternetAvailable();

        if (Catalog.getInstance().getCatalog().size() == 0) {
            Catalog.getInstance().getCatalog().clear();
            Catalog.getInstance().getManufacturers().clear();
            Catalog.getInstance().setSelectedProduct(null);
            getWebsite();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        screenView = inflater.inflate(R.layout.fragment_products, container, false);
        Spinner spinner = screenView.findViewById(R.id.spinnerCategory);
        ArrayList<String> spinnerList = new ArrayList<>(categoryMap.keySet());
        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (Catalog.getInstance().getCurrentCategoryIndex() == -1){
            Catalog.getInstance().setCurrentCategoryIndex(0);
            spinner.setSelection(0);
        }
        spinner.setSelection(Catalog.getInstance().getCurrentCategoryIndex());

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Catalog.getInstance().setCurrentCategoryIndex(parent.getSelectedItemPosition());
                showCatalog();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener);

        TextView tvMessage = (TextView)screenView.findViewById(R.id.textViewMessage);
        /*
        if (isNetworkAvailable(getContext())){
            tvMessage.setText("Connection good");
        } else {
            tvMessage.setText("Not connection");
        }
*/
        return screenView;
    }

    public void isInternetAvailable() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    InetAddress ipAddr = InetAddress.getByName("www.google.com");
                    if (!ipAddr.equals("")){
                        builder.append("Internet available");
                    } else {
                        builder.append("Internet not available");
                    }
                } catch (IOException ignore) {}

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView tvMessage = (TextView)screenView.findViewById(R.id.textViewMessage);
                        tvMessage.setText(builder.toString());
                    }
                });
            }
        }).start();
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    for (HashMap.Entry<String, String> entry : categoryMap.entrySet()) {
                        //System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());

                        Document doc = Jsoup.connect(SITE_URL + entry.getValue()).get();

                        Elements products = doc.getElementsByClass("ps-product");
                        for (Element element : products) {

                            Element product = element.getElementsByClass("ps-product__thumbnail").get(0);
                            String productURL = SITE_URL + product.getElementsByTag("a").get(0).attr("href");
                            String imgURL = SITE_URL + product.getElementsByTag("img").get(0).attr("src");
                            String description = element.getElementsByClass("ps-product__title").get(0).text();
                            String priceStr = element.getElementsByClass("ps-product__price").get(0).text();
                            int price = 0;
                            try {
                                price = Integer.parseInt(priceStr.substring(0, priceStr.indexOf(" руб."))) * 100 + Integer.parseInt(priceStr.substring(priceStr.indexOf(" руб.") + 6, priceStr.indexOf(" коп.")));
                            } catch (Exception e) {
                                builder.append("Error : ").append(e.getMessage()).append("\n");
                            }
                            Bitmap image = null;
                            try {
                                URL url = new URL(imgURL);
                                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            } catch (IOException e) {
                                builder.append("Error : ").append(e.getMessage()).append("\n");
                            }
                            Product product1 = new Product(entry.getKey(), "", description, productURL, image, price);
                            Catalog.getInstance().getCatalog().add(product1);
                        }

                        Elements manufacturers = doc.getElementsByClass("ps-filter__content").get(0).getElementsByAttributeValue("name", "filter[Производитель][]");
                        for (Element manufacturer : manufacturers) {
                            Catalog.getInstance().getManufacturers().add(new Pair<>(entry.getKey(), manufacturer.val()));
                        }

                        for (Product product : Catalog.getInstance().getCatalogByCategory(entry.getKey())) {
                            for (String manufacturer : Catalog.getInstance().getManufacturersByCategory(entry.getKey())) {
                                if (product.getDescription().toLowerCase().contains(manufacturer.toLowerCase())) {
                                    product.setManufacturer(manufacturer);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e){
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                showCatalog();
            }
        }).start();
    }

    private void showCatalog(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Spinner spinner = screenView.findViewById(R.id.spinnerCategory);
                String selectedCategory = spinner.getSelectedItem().toString();
                RecyclerView recyclerView = screenView.findViewById(R.id.list);
                recyclerView.addItemDecoration(new DividerItemDecoration(screenView.getContext(), LinearLayoutManager.VERTICAL));
                ProductAdapter adapter = new ProductAdapter(screenView.getContext(), Catalog.getInstance().getCatalogByCategoryWithFilter(selectedCategory));
                recyclerView.setAdapter(adapter);
            }
        });
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}