package tiendhph30203.poly.AndroidNetworkingNew;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import tiendhph30203.poly.AndroidNetworkingNew.DanhSachTruyen.DanhSachTruyenFragment;
import tiendhph30203.poly.AndroidNetworkingNew.ManHinhChiTiet.ManHinhChiTiet;

public class ManHinhChinh extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FrameLayout frameLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_man_hinh_chinh);
        toolbar = findViewById(R.id.Toobar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.Nav);
        frameLayout = findViewById(R.id.LayoutConten);
        drawerLayout = findViewById(R.id.Drawer);
        // Retrieve IdUser from Intent

        if (savedInstanceState == null) {
            replaceFragment(DanhSachTruyenFragment.newInstance(), "Danh sách truyện");

        }
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(ManHinhChinh.this, drawerLayout, toolbar, 0, 0);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
//        if (id == R.id.itDanhSachTruyen) {
//            toolbar.setTitle(item.getTitle());
//            replaceFragment(DanhSachTruyenFragment.newInstance(), "Danh sách truyện");
//            String IdUser = getIntent().getStringExtra("IdUser");
//            // Tạo bundle để chứa IdUser
//            Bundle bundle = new Bundle();
//            bundle.putString("IdUser", IdUser);
//            DanhSachTruyenFragment danhSachTruyenFragment = new DanhSachTruyenFragment();
//            danhSachTruyenFragment.setArguments(bundle);
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, danhSachTruyenFragment)
//                    .commit();
//            drawerLayout.close();

//        else if (id == R.id.itSanPham) {
//            toolbar.setTitle(item.getTitle());
//            repLaceFragment(SanPhamFragment.newInstance());
//            drawerLayout.close();
//        } else if (id == R.id.itQuanLyHoaDon) {
//            toolbar.setTitle(item.getTitle());
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            Fragment fragment = null;
//            fragment = new Tablayoutactivity();
//            drawerLayout.close();
//            fragmentManager.beginTransaction().replace(R.id.LayoutConten, fragment).commit();
//        } else if (id == R.id.itQuanLyDonMua) {
//            toolbar.setTitle(item.getTitle());
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            Fragment fragment = null;
//            fragment = new TablayoutDonMua();
//            drawerLayout.close();
//            fragmentManager.beginTransaction().replace(R.id.LayoutConten, fragment).commit();
//        } else if (id == R.id.itQuanLyKhachHang) {
//            toolbar.setTitle(item.getTitle());
//            repLaceFragment(QuanLyKhachHangFragment.newInstance());
//            drawerLayout.close();
//        } else if (id == R.id.itGioHang) {
//            toolbar.setTitle(item.getTitle());
//            startActivity(new Intent(MainActivity.this, GioHangActivity.class));
//            drawerLayout.close();
//        } else if (id == R.id.itThongKeDT) {
//            toolbar.setTitle(item.getTitle());
//            repLaceFragment(ThongKeDoanhThuFragment.newInstance());
//            drawerLayout.close();
//        } else if (id == R.id.itThongKeTop) {
//            toolbar.setTitle(item.getTitle());
//            repLaceFragment(ThongKeTopFragment.newInstance());
//            drawerLayout.close();
//        } else if (id == R.id.itDoiMatKhau) {
//
//            showDialogDoiMatKhau();
//            drawerLayout.close();
//        } else if (id == R.id.itDangXuat) {
//
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.clear();
//            editor.apply();

//
//        }
//        else {
////            repLaceFragment(DanhSachTruyenFragment.newInstance());
////
////            return super.onOptionsItemSelected(item);
//        }


        return true;
    }

    private void replaceFragment(Fragment fragment, String title) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.LayoutConten, fragment);
        fragmentTransaction.addToBackStack(null); // Add this line to enable back navigation
        fragmentTransaction.commit();
        setTitle(title);
    }

}