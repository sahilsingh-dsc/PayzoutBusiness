package com.payzout.business.common.banner;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.payzout.business.utils.FirestoreConstant;

import java.util.ArrayList;
import java.util.List;

public class BannerPresenter {
    private Context context;
    private BannerInterface bannerInterface;

    public BannerPresenter(Context context, BannerInterface bannerInterface) {
        this.context = context;
        this.bannerInterface = bannerInterface;
    }

    public void fetchBanners() {
        final List<Banner> bannerList = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirestoreConstant.BANNER_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.getResult().getDocuments().isEmpty()) {
                            bannerInterface.bannerNoFound("No Banners");
                        } else {
                            for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                                Banner banner = snapshot.toObject(Banner.class);
                                bannerList.add(banner);
                            }
                            bannerInterface.bannerFetchSuccess(bannerList);
                        }
                    }
                });
    }

}
