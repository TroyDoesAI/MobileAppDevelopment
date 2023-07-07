
import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\bJ\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0004H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ$\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000e0\r2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u0007H\u0002\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0011"}, d2 = {"LApiHandler;", "", "()V", "getChannels", "", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Channel;", "workspaceId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getWorkspaces", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Workspace;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "makeHttpRequest", "Lkotlin/Pair;", "", "method", "endpt", "app_debug"})
public final class ApiHandler {
    @org.jetbrains.annotations.NotNull
    public static final ApiHandler INSTANCE = null;
    
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
}