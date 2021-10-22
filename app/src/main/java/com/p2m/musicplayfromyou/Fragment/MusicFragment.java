package com.p2m.musicplayfromyou.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.p2m.musicplayfromyou.Adapter.ALbumAdapter;
import com.p2m.musicplayfromyou.Adapter.CircleAdapter;
import com.p2m.musicplayfromyou.Adapter.LangAdapter;
import com.p2m.musicplayfromyou.Adapter.SongOpenerAdapter;
import com.p2m.musicplayfromyou.Models.albumModel;
import com.p2m.musicplayfromyou.R;

import java.util.ArrayList;

public class MusicFragment extends Fragment {
    public String[] imagesl= new String[] {"https://images.unsplash.com/photo-1556742044-3c52d6e88c62?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1473711789055-df5beb0e35ce?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1498579687545-d5a4fffb0a9e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1494972688394-4cc796f9e4c5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1520190282873-afe1285c9a2a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1499696010180-025ef6e1a8f9?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1520190282873-afe1285c9a2a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1613431812949-77b3351bb23d?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1525190094219-c84e1d747b14?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1572281158640-30040dd70cbe?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1563127673-00fb29e7eeae?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1559076294-ad5d97e1e7c4?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1550355191-aa8a80b41353?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OXx8c21hbGwlMjBzaXplfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1614350292382-c448d0110dfa?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1508640622828-7375eaf31253?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612620486050-1462932b56e4?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1492540747731-d05a66dc2461?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTl8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1615531880394-84fde621fcfc?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1547393429-098dd122091a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612160890964-809a160ac4f2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1565378435089-7b0ff45a898e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjR8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612534078221-2663ab17dfde?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1555363453-81c835456fff?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1536427824649-fbf2e4a33d40?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjd8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1524727382649-12b8e6691ea2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjl8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1541497327795-b1f0252511c7?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1614350048512-98ef79f6f829?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612193952040-72ba0af96f20?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzJ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1613843873231-1447db182f97?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1556742205-e10c9486e506?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mjh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1556741568-055d848f8bfd?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1618439482734-24894d52ccef?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612537785055-e226dae15987?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzd8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1580428354768-03a028646bc8?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzR8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1620695072315-67de34371766?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzl8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1543413305-ea22e5cc007b?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1556745763-1a6f0ddb0250?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1613841014882-a26725f84f95?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDJ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1559261567-2f844618aabf?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1549069786-641f4cb652c7?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1613843873244-983a4ad83b5d?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1551927411-95e412943b58?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDl8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTJ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1533471413-e1b19c94986c?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NDh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1587116215900-bb2bba7c7cff?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1604256913790-f0702dea4cd5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1604281664444-a2a6e51223bb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTR8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1509645470620-c9c349934693?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTd8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1604256913743-008bc1c585fb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1523367310297-83064fc42a16?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1528475478853-5b89bed65c4c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1508022713622-df2d8fb7b4cd?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjR8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1604256912726-3553279c0ebf?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NTh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612128556773-b6616cf7f203?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1466442809795-adcee6572a67?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/flagged/photo-1556438758-8d49568ce18e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njl8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1495627744014-5595face04d4?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjJ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612537785084-06a08fc52b11?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1500021804447-2ca2eaaaabeb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NjV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1562679299-d21b8e13ac09?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Njh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1470608756445-2c9906b0680f?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1620216464337-69f08c564cf1?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nzh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1531170960116-aa500027308c?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzJ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1620216464126-547af1ddc8e5?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nzd8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1626077641207-54883482bb0a?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1619799131787-e18f5cbce366?ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1614383686443-6f7c99dcb690?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NzZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1620216463867-8c2a8463c253?ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODd8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1563009400-c01f7db40072?ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODV8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1546476472-888994447a5d?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTB8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1599108859519-8ac78fd1b912?ixid=MnwxMjA3fDB8MHxzZWFyY2h8ODl8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1614529303008-4762ea020a49?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTJ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1489015024470-9847bf52b0b6?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTN8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1578461271674-31b0b7c036b2?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTF8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1518831959646-742c3a14ebf7?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTh8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/flagged/photo-1553056011-7811272649cb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTR8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1625681616720-26261424fe4e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTAxfHxzbWFsbCUyMHNpemV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1614529303865-45bb81454e67?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTAwfHxzbWFsbCUyMHNpemV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1612540943977-98ce54bea8a1?ixid=MnwxMjA3fDB8MHxzZWFyY2h8OTZ8fHNtYWxsJTIwc2l6ZXxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60","https://images.unsplash.com/photo-1614529302949-454642ca0a06?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTA1fHxzbWFsbCUyMHNpemV8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"};
    public String[] names = new String[] {"Arjist singh", "Tony walker", "Shreya ghoshal", "New trending", "Badshah","Sajna","2021", "Local Trending", "Dance", "Hiphop", "Hollywood", "English","Hindi", "Punjabi", "Punjabi Hits"};
    public RecyclerView recOne, recTwo, recThree, recFour, recFive, recSix, recSeven, recEight;
    public ALbumAdapter adapter;
    public CircleAdapter adapterTwo;
    public SongOpenerAdapter adapterThree;
    public ArrayList<String> langauges = new ArrayList<>();
    public LangAdapter adapterFour;


    public MusicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        recOne = view.findViewById(R.id.recOne);
        recOne.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recTwo = view.findViewById(R.id.recTwo);
        recTwo.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recThree = view.findViewById(R.id.recThree);
        recThree.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recFour = view.findViewById(R.id.recFour);
        recFive = view.findViewById(R.id.recFive);
        recFour.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recFive.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recSix = view.findViewById(R.id.recSix);
        recSix.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recSeven = view.findViewById(R.id.recSeven);
        recSeven.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recEight = view.findViewById(R.id.recEight);
        recEight.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<albumModel> tempArr = new ArrayList<>();
        addLang();

        for (int i = 0; i < names.length; i++) {
            albumModel single = new albumModel();
            single.setImage(imagesl[i]);
            single.setName(names[i]);
            tempArr.add(single);
        }

        adapter = new ALbumAdapter(tempArr, getContext());
        adapterTwo = new CircleAdapter(tempArr, getContext());
        adapterThree = new SongOpenerAdapter( tempArr, getContext());
        adapterFour = new LangAdapter(langauges, getContext());
        recOne.setAdapter(adapter);
        recTwo.setAdapter(adapterTwo);
        recThree.setAdapter(adapterThree);
        recFour.setAdapter(adapterFour);
        recFive.setAdapter(adapterFour);
        recSix.setAdapter(adapter);
        recSeven.setAdapter(adapterThree);
        recEight.setAdapter(adapter);


    }
    public void  addLang(){
        langauges.add("English");
        langauges.add("Hindi");
        langauges.add("Tamil");
        langauges.add("DJ");
        langauges.add("Punjabi");
        langauges.add("Gujarati");
        langauges.add("Hiphop");
        langauges.add("Chinese");
        langauges.add("Classic");
        langauges.add("Romance");
        langauges.add("Love");
        langauges.add("Rock");
        langauges.add("Loud");
    }
}

















