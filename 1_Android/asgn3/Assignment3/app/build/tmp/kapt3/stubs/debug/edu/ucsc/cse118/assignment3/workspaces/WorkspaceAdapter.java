package edu.ucsc.cse118.assignment3.workspaces;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0013B\'\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\u0002\u0010\tJ\b\u0010\n\u001a\u00020\u000bH\u0016J\u001c\u0010\f\u001a\u00020\b2\n\u0010\r\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000bH\u0016J\u001c\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u000bH\u0016R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Ledu/ucsc/cse118/assignment3/workspaces/WorkspaceAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Ledu/ucsc/cse118/assignment3/workspaces/WorkspaceAdapter$WorkspaceViewHolder;", "workspaces", "", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Workspace;", "listener", "Lkotlin/reflect/KFunction1;", "", "(Ljava/util/List;Lkotlin/reflect/KFunction;)V", "getItemCount", "", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "WorkspaceViewHolder", "app_debug"})
public final class WorkspaceAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<edu.ucsc.cse118.assignment3.workspaces.WorkspaceAdapter.WorkspaceViewHolder> {
    private final java.util.List<edu.ucsc.cse118.assignment3.data.DataClasses.Workspace> workspaces = null;
    private final kotlin.reflect.KFunction<kotlin.Unit> listener = null;
    
    public WorkspaceAdapter(@org.jetbrains.annotations.NotNull
    java.util.List<edu.ucsc.cse118.assignment3.data.DataClasses.Workspace> workspaces, @org.jetbrains.annotations.NotNull
    kotlin.reflect.KFunction<kotlin.Unit> listener) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public edu.ucsc.cse118.assignment3.workspaces.WorkspaceAdapter.WorkspaceViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    edu.ucsc.cse118.assignment3.workspaces.WorkspaceAdapter.WorkspaceViewHolder holder, int position) {
    }
    
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Ledu/ucsc/cse118/assignment3/workspaces/WorkspaceAdapter$WorkspaceViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "view", "Landroid/view/View;", "(Ledu/ucsc/cse118/assignment3/workspaces/WorkspaceAdapter;Landroid/view/View;)V", "channelsTextView", "Landroid/widget/TextView;", "name", "bind", "", "workspace", "Ledu/ucsc/cse118/assignment3/data/DataClasses$Workspace;", "app_debug"})
    public final class WorkspaceViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final android.widget.TextView name = null;
        private final android.widget.TextView channelsTextView = null;
        
        public WorkspaceViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View view) {
            super(null);
        }
        
        public final void bind(@org.jetbrains.annotations.NotNull
        edu.ucsc.cse118.assignment3.data.DataClasses.Workspace workspace) {
        }
    }
}