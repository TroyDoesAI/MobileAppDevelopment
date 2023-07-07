package edu.ucsc.cse118.assignment3;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0019\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ\u001f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00042\u0006\u0010\u0010\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\rJ$\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u0007H\u0002\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0018"}, d2 = {"Ledu/ucsc/cse118/assignment3/ApiHandler;", "", "()V", "getChannels", "", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Channel;", "workspaceId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMemberById", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Member;", "memberId", "getMembers", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMessages", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Message;", "channelId", "getWorkspaces", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Workspace;", "makeHttpRequest", "Lkotlin/Pair;", "", "method", "endpt", "app_debug"})
public final class ApiHandler {
    @org.jetbrains.annotations.NotNull
    public static final edu.ucsc.cse118.assignment3.ApiHandler INSTANCE = null;
    
    private ApiHandler() {
        super();
    }
    
    private final kotlin.Pair<java.lang.String, java.lang.Integer> makeHttpRequest(java.lang.String method, java.lang.String endpt) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getWorkspaces(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<edu.ucsc.cse118.assignment3.data.DataClasses.Workspace>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getChannels(@org.jetbrains.annotations.NotNull
    java.lang.String workspaceId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<edu.ucsc.cse118.assignment3.data.DataClasses.Channel>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getMessages(@org.jetbrains.annotations.NotNull
    java.lang.String channelId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<edu.ucsc.cse118.assignment3.data.DataClasses.Message>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getMembers(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.util.List<edu.ucsc.cse118.assignment3.data.DataClasses.Member>> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getMemberById(@org.jetbrains.annotations.NotNull
    java.lang.String memberId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super edu.ucsc.cse118.assignment3.data.DataClasses.Member> continuation) {
        return null;
    }
}