package com.zulfikar.studentportal.forum;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zulfikar.studentportal.R;

public class ForumFragment extends Fragment {

    RecyclerView recyclerViewPostCards;
    View rootView;

    String TAG = "ForumFragment";

    public ForumFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView = inflater.inflate(R.layout.fragment_forum, container, false);
        recyclerViewPostCards = rootView.findViewById(R.id.recyclerViewPostCards);
        String defaultUserPhoto = "https://cdn.discordapp.com/attachments/820979752268464128/916281082947715092/userphoto_default.png";

        PostCard postCard = new PostCard(0, 1, 0, 0,
                "Mohammad Zulfikar Ali Mahbub", defaultUserPhoto,
                "CSE391", "Fall 2021",
                "সমাজের কাছে হরে যায় আমাদের ইচ্ছে গুলো বাবা মা বুঝবে কবে আমাদেরো কিছু ইচ্ছে ছিলো, তাদের চোখ দিয়ে স্বপ্ন দেখতে বলে, আমাদেরো চোখ আছে তা বুঝবে তারা কোন কালে? স্বপ্ন গুলো এভাবে দুমড়ে মুচড়ে যায়, জীবনের শেষ অংশে হতাশা পাই শুধু ঠাঁই।- \"Abu Hasnayen Zillanee\"",
                "Thursday, November 11, 2021 at 7:34 AM", "11K",
                "3.6K", new CommentCard[]{
                        new CommentCard(0, 2, 0, "আবু হাসনাইন জিল্লানি", defaultUserPhoto, "যদিও এখনো পাওনা শোধ হয়নি, তবুও শুভ জন্মদিন বন্ধু।", "4mins"),
                        new CommentCard(1, 3, 0, "G M Sohanur Rahman", defaultUserPhoto, "Why rest when you can be best? xDDDD", "Yesterday at 5:18 PM")
        });
        PostCard postCard2 = new PostCard(1, 2, 1, 0,
                "আবু হাসনাইন জিল্লানি", defaultUserPhoto,
                "CSE341", "Fall 2021",
                "Happy birthday to the most sentiখোড় মহিলা | মানে এতো কেন সেন্টি খাস | মাঝে মাঝে ভয় পাওয়ায় দেস | যাই হোক আজ তোর দিন, দিনটা উপভোগ কর, মজা কর, আর এই বার্থডে তে নো গিফট ফ্রোম মাইন | আমার পাওনা গুলা সোধ কর আগে।| \uD83D\uDE05 কিভাবে কিভাবে যেনো বন্ধু্ত্বটা হলো আবার এতগুলো সময় পেরিয়ে গেলো। মাঝে মাঝে মানুষ যখন জিজ্ঞাসা করে তোর সাথে বন্ধুত্ব কিভাবে, বলতে গিয়ে আমিও হারিয়ে যাই |",
                "Thursday, November 11, 2021 at 7:34 AM", "11K",
                "3.6K", new CommentCard[]{
                        new CommentCard(2, 4, 1, "Sayed Md. Rahmat Ullah Afsan", defaultUserPhoto, "আসলে তুই একটা ব্লেসিং !!!", "November 1, 2021 at 4:13 PM"),
//                        new CommentCard(3, 3, 1, "G M Sohanur Rahman", defaultUserPhoto, "exam will start from 6.30", "October 16, 2021 at 5:08 PM")
        });

        Log.d(TAG, "onCreateView: " + recyclerViewPostCards);

        PostCardAdapter postCardAdapter = new PostCardAdapter(container.getContext(), new PostCard[]{postCard, postCard2});
        recyclerViewPostCards.setAdapter(postCardAdapter);
        recyclerViewPostCards.setLayoutManager(new LinearLayoutManager(container.getContext()));
        return rootView;
    }
}