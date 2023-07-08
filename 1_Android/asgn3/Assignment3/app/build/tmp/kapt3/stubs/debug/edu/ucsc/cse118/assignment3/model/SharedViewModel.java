package edu.ucsc.cse118.assignment3.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u0000 \u00142\u00020\u0001:\u0002\u0014\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J!\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013R\u001a\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u001d\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00050\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0016"}, d2 = {"Ledu/ucsc/cse118/assignment3/model/SharedViewModel;", "Landroidx/lifecycle/ViewModel;", "()V", "_loginEvent", "Landroidx/lifecycle/MutableLiveData;", "Ledu/ucsc/cse118/assignment3/model/ViewModelEvent;", "", "_navigateToWorkspacesEvent", "", "loginEvent", "Landroidx/lifecycle/LiveData;", "getLoginEvent", "()Landroidx/lifecycle/LiveData;", "navigateToWorkspacesEvent", "getNavigateToWorkspacesEvent", "loginUser", "email", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "LoginCredentials", "app_debug"})
public final class SharedViewModel extends androidx.lifecycle.ViewModel {
    private final androidx.lifecycle.MutableLiveData<edu.ucsc.cse118.assignment3.model.ViewModelEvent<java.lang.Boolean>> _loginEvent = null;
    private final androidx.lifecycle.MutableLiveData<edu.ucsc.cse118.assignment3.model.ViewModelEvent<kotlin.Unit>> _navigateToWorkspacesEvent = null;
    @org.jetbrains.annotations.NotNull
    public static final edu.ucsc.cse118.assignment3.model.SharedViewModel.Companion Companion = null;
    @org.jetbrains.annotations.Nullable
    private static edu.ucsc.cse118.assignment3.model.Member member;
    @org.jetbrains.annotations.NotNull
    public static final java.lang.String url = "https://cse118.com/api/v2/login";
    
    public SharedViewModel() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<edu.ucsc.cse118.assignment3.model.ViewModelEvent<java.lang.Boolean>> getLoginEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.LiveData<edu.ucsc.cse118.assignment3.model.ViewModelEvent<kotlin.Unit>> getNavigateToWorkspacesEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object loginUser(@org.jetbrains.annotations.NotNull
    java.lang.String email, @org.jetbrains.annotations.NotNull
    java.lang.String password, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\b\u0018\u0000 \u001e2\u00020\u0001:\u0002\u001d\u001eB-\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tB\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\nJ\t\u0010\u000e\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0005H\u00d6\u0001J!\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u00c7\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\f\u00a8\u0006\u001f"}, d2 = {"Ledu/ucsc/cse118/assignment3/model/SharedViewModel$LoginCredentials;", "", "seen1", "", "email", "", "password", "serializationConstructorMarker", "Lkotlinx/serialization/internal/SerializationConstructorMarker;", "(ILjava/lang/String;Ljava/lang/String;Lkotlinx/serialization/internal/SerializationConstructorMarker;)V", "(Ljava/lang/String;Ljava/lang/String;)V", "getEmail", "()Ljava/lang/String;", "getPassword", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "write$Self", "", "self", "output", "Lkotlinx/serialization/encoding/CompositeEncoder;", "serialDesc", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "$serializer", "Companion", "app_debug"})
    @kotlinx.serialization.Serializable
    public static final class LoginCredentials {
        @org.jetbrains.annotations.NotNull
        public static final edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials.Companion Companion = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String email = null;
        @org.jetbrains.annotations.NotNull
        private final java.lang.String password = null;
        
        @org.jetbrains.annotations.NotNull
        public final edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials copy(@org.jetbrains.annotations.NotNull
        java.lang.String email, @org.jetbrains.annotations.NotNull
        java.lang.String password) {
            return null;
        }
        
        @java.lang.Override
        public boolean equals(@org.jetbrains.annotations.Nullable
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        public java.lang.String toString() {
            return null;
        }
        
        @kotlin.jvm.JvmStatic
        public static final void write$Self(@org.jetbrains.annotations.NotNull
        edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials self, @org.jetbrains.annotations.NotNull
        kotlinx.serialization.encoding.CompositeEncoder output, @org.jetbrains.annotations.NotNull
        kotlinx.serialization.descriptors.SerialDescriptor serialDesc) {
        }
        
        public LoginCredentials(@org.jetbrains.annotations.NotNull
        java.lang.String email, @org.jetbrains.annotations.NotNull
        java.lang.String password) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getEmail() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.lang.String getPassword() {
            return null;
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u00c6\u0001\u00a8\u0006\u0006"}, d2 = {"Ledu/ucsc/cse118/assignment3/model/SharedViewModel$LoginCredentials$Companion;", "", "()V", "serializer", "Lkotlinx/serialization/KSerializer;", "Ledu/ucsc/cse118/assignment3/model/SharedViewModel$LoginCredentials;", "app_debug"})
        public static final class Companion {
            
            private Companion() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            public final kotlinx.serialization.KSerializer<edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials> serializer() {
                return null;
            }
        }
        
        @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u00006\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0018\u0010\b\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\n0\tH\u00d6\u0001\u00a2\u0006\u0002\u0010\u000bJ\u0011\u0010\f\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0002H\u00d6\u0001R\u0014\u0010\u0004\u001a\u00020\u00058VX\u00d6\u0005\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0014"}, d2 = {"edu/ucsc/cse118/assignment3/model/SharedViewModel.LoginCredentials.$serializer", "Lkotlinx/serialization/internal/GeneratedSerializer;", "Ledu/ucsc/cse118/assignment3/model/SharedViewModel$LoginCredentials;", "()V", "descriptor", "Lkotlinx/serialization/descriptors/SerialDescriptor;", "getDescriptor", "()Lkotlinx/serialization/descriptors/SerialDescriptor;", "childSerializers", "", "Lkotlinx/serialization/KSerializer;", "()[Lkotlinx/serialization/KSerializer;", "deserialize", "decoder", "Lkotlinx/serialization/encoding/Decoder;", "serialize", "", "encoder", "Lkotlinx/serialization/encoding/Encoder;", "value", "app_debug"})
        @java.lang.Deprecated
        public static final class $serializer implements kotlinx.serialization.internal.GeneratedSerializer<edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials> {
            @org.jetbrains.annotations.NotNull
            public static final edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials.$serializer INSTANCE = null;
            
            private $serializer() {
                super();
            }
            
            @org.jetbrains.annotations.NotNull
            @java.lang.Override
            public kotlinx.serialization.KSerializer<?>[] childSerializers() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            @java.lang.Override
            public edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials deserialize(@org.jetbrains.annotations.NotNull
            kotlinx.serialization.encoding.Decoder decoder) {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull
            @java.lang.Override
            public kotlinx.serialization.descriptors.SerialDescriptor getDescriptor() {
                return null;
            }
            
            @java.lang.Override
            public void serialize(@org.jetbrains.annotations.NotNull
            kotlinx.serialization.encoding.Encoder encoder, @org.jetbrains.annotations.NotNull
            edu.ucsc.cse118.assignment3.model.SharedViewModel.LoginCredentials value) {
            }
            
            @org.jetbrains.annotations.NotNull
            public kotlinx.serialization.KSerializer<?>[] typeParametersSerializers() {
                return null;
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Ledu/ucsc/cse118/assignment3/model/SharedViewModel$Companion;", "", "()V", "member", "Ledu/ucsc/cse118/assignment3/model/Member;", "getMember", "()Ledu/ucsc/cse118/assignment3/model/Member;", "setMember", "(Ledu/ucsc/cse118/assignment3/model/Member;)V", "url", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        public final edu.ucsc.cse118.assignment3.model.Member getMember() {
            return null;
        }
        
        public final void setMember(@org.jetbrains.annotations.Nullable
        edu.ucsc.cse118.assignment3.model.Member p0) {
        }
    }
}