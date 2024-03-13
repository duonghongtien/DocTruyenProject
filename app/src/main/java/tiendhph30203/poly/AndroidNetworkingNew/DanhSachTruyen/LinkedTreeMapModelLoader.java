package tiendhph30203.poly.AndroidNetworkingNew.DanhSachTruyen;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.signature.ObjectKey;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class LinkedTreeMapModelLoader implements ModelLoader<LinkedTreeMap<?, ?>, InputStream> {

    @Override
    public LoadData<InputStream> buildLoadData(LinkedTreeMap<?, ?> model, int width, int height, Options options) {
        return new ModelLoader.LoadData<>(new ObjectKey(model), new LinkedTreeMapDataFetcher(model));
    }

    @Override
    public boolean handles(LinkedTreeMap<?, ?> model) {
        return model instanceof LinkedTreeMap;
    }

    public static class Factory implements ModelLoaderFactory<LinkedTreeMap<?, ?>, InputStream> {

        @Override
        public ModelLoader<LinkedTreeMap<?, ?>, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new LinkedTreeMapModelLoader();
        }

        @Override
        public void teardown() {
            // Cleanup if necessary
        }
    }

    private static class LinkedTreeMapDataFetcher implements DataFetcher<InputStream> {
        private LinkedTreeMap<?, ?> model;

        public LinkedTreeMapDataFetcher(LinkedTreeMap<?, ?> model) {
            this.model = model;
        }

        @Override
        public void loadData(Priority priority, DataCallback<? super InputStream> callback) {
            try {
                // Convert LinkedTreeMap to a byte array
                byte[] data = convertLinkedTreeMapToByteArray(model);

                // Convert byte array to InputStream
                InputStream inputStream = new ByteArrayInputStream(data);

                // Pass the InputStream to the callback
                callback.onDataReady(inputStream);
            } catch (Exception e) {
                // Handle the exception and inform the callback about the failure
                callback.onLoadFailed(e);
            }
        }

        private byte[] convertLinkedTreeMapToByteArray(LinkedTreeMap<?, ?> linkedTreeMap) {
            // Convert LinkedTreeMap to JSON string
            Gson gson = new Gson();
            String jsonString = gson.toJson(linkedTreeMap);

            // Convert JSON string to byte array
            return jsonString.getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public void cleanup() {
            // Cleanup resources if necessary
        }

        @Override
        public void cancel() {
            // Cancel the load operation if necessary
        }

        @Override
        public Class<InputStream> getDataClass() {
            return InputStream.class;
        }

        @Override
        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }
    }
}
