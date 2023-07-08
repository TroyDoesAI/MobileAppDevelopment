package edu.ucsc.cse118.assignment3.messages;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0017B?\u0012\u0018\u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004\u0012\u001e\u0010\b\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eJ\b\u0010\u000f\u001a\u00020\u000eH\u0016J\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\u0006\u0010\r\u001a\u00020\u000eJ\u0018\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u000eH\u0016R \u0010\u0003\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00050\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R&\u0010\b\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Ledu/ucsc/cse118/assignment3/messages/MessageAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Ledu/ucsc/cse118/assignment3/messages/MessageAdapter$MessageViewHolder;", "messageMembers", "", "Lkotlin/Pair;", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Message;", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Member;", "onMessageClicked", "Lkotlin/Function1;", "", "(Ljava/util/List;Lkotlin/jvm/functions/Function1;)V", "deleteMessage", "position", "", "getItemCount", "getMessageMemberPair", "onBindViewHolder", "holder", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "MessageViewHolder", "app_debug"})
public final class MessageAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<edu.ucsc.cse118.assignment3.messages.MessageAdapter.MessageViewHolder> {
    private java.util.List<kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member>> messageMembers;
    private final kotlin.jvm.functions.Function1<kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member>, kotlin.Unit> onMessageClicked = null;
    
    public MessageAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member>> messageMembers, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member>, kotlin.Unit> onMessageClicked) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public edu.ucsc.cse118.assignment3.messages.MessageAdapter.MessageViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    edu.ucsc.cse118.assignment3.messages.MessageAdapter.MessageViewHolder holder, int position) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member> getMessageMemberPair(int position) {
        return null;
    }
    
    public final void deleteMessage(int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J:\u0010\t\u001a\u00020\n2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f2\u001e\u0010\u000f\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f\u0012\u0004\u0012\u00020\n0\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Ledu/ucsc/cse118/assignment3/messages/MessageAdapter$MessageViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "content", "Landroid/widget/TextView;", "posted", "userName", "bind", "", "messageMember", "Lkotlin/Pair;", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Message;", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Member;", "onMessageClicked", "Lkotlin/Function1;", "app_debug"})
    public static final class MessageViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final android.widget.TextView userName = null;
        private final android.widget.TextView posted = null;
        private final android.widget.TextView content = null;
        
        public MessageViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member> messageMember, @org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super kotlin.Pair<edu.ucsc.cse118.assignment3.data.DataClasses.Message, edu.ucsc.cse118.assignment3.data.DataClasses.Member>, kotlin.Unit> onMessageClicked) {
        }
    }
}