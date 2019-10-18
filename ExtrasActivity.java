public class ExtrasActivity extends AppCompatActivity implements BlockViewHolder.OnItemListener {


    public DatabaseReference mDataRef, $SongUrlref, SongPlayingRef, second_position, second_position_name_ref, second_postion_artist_ref;
    private RecyclerView mRecyclerView;
    private BlockViewHolder blockViewHolder;
    private ValueEventListener dblistener;
    private List<Block> mBlock;
    private ImageView corrosponding_mage;
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extralayout);


        mDataRef = FirebaseDatabase.getInstance().getReference("TrendingSongList");
        mDataRef.keepSynced(true);
        $SongUrlref = FirebaseDatabase.getInstance().getReference("TrendingSongList").child("LehangaTrending").child("songUrl");

        SongPlayingRef = FirebaseDatabase.getInstance().getReference("TrendingSongList").child("LehangaTrending").child("SongName");

        second_position_name_ref = FirebaseDatabase.getInstance().getReference("TrendingSongList")
                .child("Beautiful").child("SongName");

        second_position = FirebaseDatabase.getInstance().getReference("TrendingSongList").child("Beautiful").child("songUrl");

        mRecyclerView = findViewById(R.id.extra_recylerview);
        corrosponding_mage = findViewById(R.id.extras_image);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBlock = new ArrayList<>();

        blockViewHolder = new BlockViewHolder(getApplicationContext(), mBlock, this);

        mRecyclerView.setAdapter(blockViewHolder);


        dblistener = mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mBlock.clear();
                // sendOnChannel2();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Block block = postSnapshot.getValue(Block.class);
                    mBlock.add(block);
                    //  sendOnChannel2();

                }

                blockViewHolder.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onItemClick(int position) {

        if (position == 0) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            fetchAudioFromFirebaseForPositionTwoInRecyclerViewOfApp();
            SendToAnotherActivityAndGetStringsForPositionTwo();
        }

        if (position == 1) {


            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            fetchAudioUrlFromFirebaseForPositionOneInRecyclerViewOfApp();
            SendToAnotherActivityAndGetStringsForPositionOne();


        }


    }


    private void fetchAudioFromFirebaseForPositionTwoInRecyclerViewOfApp() {


    }


    private void fetchAudioUrlFromFirebaseForPositionOneInRecyclerViewOfApp() {


    }


    private void SendToAnotherActivityAndGetStringsForPositionOne() {

        SongPlayingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String SongName = dataSnapshot.getValue().toString();
                //Toast.makeText(getApplicationContext(), "" + SongName, Toast.LENGTH_SHORT).show();

                DatabaseReference ArtistRef = FirebaseDatabase.getInstance().getReference("TrendingSongList")
                        .child("LehangaTrending").child("ArtistName");

                ArtistRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String ArtistName = dataSnapshot.getValue().toString();

                        DatabaseReference SongDurationRef = FirebaseDatabase.getInstance().getReference("TrendingSongPlayingData")
                                .child("Lehanga").child("SongDuration");

                        SongDurationRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                final Double d1 = (Double) dataSnapshot.getValue();

                                DatabaseReference SongPlayingImage = FirebaseDatabase.getInstance().getReference("TrendingSongPlayingData")
                                        .child("Lehanga").child("SongImageUrl");

                                SongPlayingImage.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final String SongPlayingImageUrl = dataSnapshot.getValue().toString();

                                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TrendingSongList")
                                                .child("LehangaTrending").child("songUrl");

                                        databaseReference.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String SongUrl = dataSnapshot.getValue().toString();

                                                Intent intent = new Intent(ExtrasActivity.this, SongPlayingActivity.class);

                                                intent.putExtra("Image", SongPlayingImageUrl);
                                                intent.putExtra("Duration", d1);
                                                intent.putExtra("Artist", ArtistName);
                                                intent.putExtra("Name", SongName);
                                                intent.putExtra("SongUrl"  , SongUrl);

                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void SendToAnotherActivityAndGetStringsForPositionTwo() {

        second_position_name_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String SongName = dataSnapshot.getValue().toString();

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("TrendingSongList")
                        .child("Beautiful")
                        .child("ArtistName");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String ArtistName = dataSnapshot.getValue().toString();


                        DatabaseReference SongDurationRef = FirebaseDatabase.getInstance().getReference("TrendingSongPlayingData")
                                .child("Beautiful").child("Duration");

                        SongDurationRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final Double SongDuration = (Double) dataSnapshot.getValue();

                                DatabaseReference SongPlayingImageUrlRef = FirebaseDatabase.getInstance().getReference("TrendingSongPlayingData")
                                        .child("Beautiful").child("SongImageUrl");

                                SongPlayingImageUrlRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        final String SongPlayingUrl = dataSnapshot.getValue().toString();

                                        second_position.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 String SongUrl = dataSnapshot.getValue().toString();

                                                Intent intent = new Intent(ExtrasActivity.this, SongPlayingActivity.class);
                                                intent.putExtra("name", SongName);
                                                intent.putExtra("Artist", ArtistName);
                                                intent.putExtra("Duration", SongDuration);
                                                intent.putExtra("Image", SongPlayingUrl);
                                                intent.putExtra("SongUrl", SongUrl);

                                                startActivity(intent);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
