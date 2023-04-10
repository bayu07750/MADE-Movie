package com.bayu07750.mademovie.core.data.source.network.model.mapper

interface ResponseMapper <Response, Domain> {
    operator fun invoke (response: Response) : Domain
}