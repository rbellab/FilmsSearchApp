package eu.berngardt.remote_module

interface RemoteProvider {
    fun provideRemote(): TmdbApi
}