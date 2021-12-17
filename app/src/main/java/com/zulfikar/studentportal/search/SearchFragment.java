package com.zulfikar.studentportal.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.Assets;
import com.zulfikar.studentportal.R;
import com.zulfikar.studentportal.forum.CommentCard;
import com.zulfikar.studentportal.forum.PostCard;
import com.zulfikar.studentportal.forum.PostCardAdapter;

public class SearchFragment extends Fragment {

    View rootView;
    RecyclerView rvPostCards;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        rvPostCards = rootView.findViewById(R.id.rvPostCards);
        PostCard postCard = new PostCard(0, 1, 0, 0,
                "Mohammad Zulfikar Ali Mahbub", Assets.defaultImg,
                "CSE391", "Fall 2021",
                "সমাজের কাছে হরে যায় আমাদের ইচ্ছে গুলো বাবা মা বুঝবে কবে আমাদেরো কিছু ইচ্ছে ছিলো, তাদের চোখ দিয়ে স্বপ্ন দেখতে বলে, আমাদেরো চোখ আছে তা বুঝবে তারা কোন কালে? স্বপ্ন গুলো এভাবে দুমড়ে মুচড়ে যায়, জীবনের শেষ অংশে হতাশা পাই শুধু ঠাঁই।- \"Abu Hasnayen Zillanee\"",
                "Thursday, November 11, 2021 at 7:34 AM", "11K",
                "3.6K", new CommentCard[]{
                new CommentCard(0, 2, 0, "আবু হাসনাইন জিল্লানি", Assets.defaultImg, "যদিও এখনো পাওনা শোধ হয়নি, তবুও শুভ জন্মদিন বন্ধু।", "4mins"),
                new CommentCard(1, 3, 0, "G M Sohanur Rahman", Assets.defaultImg, "Why rest when you can be best? xDDDD", "Yesterday at 5:18 PM")
        });
        PostCard postCard2 = new PostCard(1, 2, 1, 0,
                "আবু হাসনাইন জিল্লানি", Assets.defaultImg,
                "CSE341", "Fall 2021",
                "Happy birthday to the most sentiখোড় মহিলা | মানে এতো কেন সেন্টি খাস | মাঝে মাঝে ভয় পাওয়ায় দেস | যাই হোক আজ তোর দিন, দিনটা উপভোগ কর, মজা কর, আর এই বার্থডে তে নো গিফট ফ্রোম মাইন | আমার পাওনা গুলা সোধ কর আগে।| \uD83D\uDE05 কিভাবে কিভাবে যেনো বন্ধু্ত্বটা হলো আবার এতগুলো সময় পেরিয়ে গেলো। মাঝে মাঝে মানুষ যখন জিজ্ঞাসা করে তোর সাথে বন্ধুত্ব কিভাবে, বলতে গিয়ে আমিও হারিয়ে যাই |",
                "Thursday, November 11, 2021 at 7:34 AM", "11K",
                "3.6K", new CommentCard[]{
                new CommentCard(2, 4, 1, "Sayed Md. Rahmat Ullah Afsan", Assets.defaultImg, "আসলে তুই একটা ব্লেসিং !!!", "November 1, 2021 at 4:13 PM"),
                new CommentCard(3, 3, 1, "G M Sohanur Rahman", Assets.defaultImg, "exam will start from 6.30", "October 16, 2021 at 5:08 PM")
        });

        PostCardAdapter postCardAdapter = new PostCardAdapter(container.getContext(), new PostCard[]{postCard, postCard2});
        rvPostCards.setAdapter(postCardAdapter);
        rvPostCards.setLayoutManager(new LinearLayoutManager(container.getContext()));

        return rootView;
    }
}