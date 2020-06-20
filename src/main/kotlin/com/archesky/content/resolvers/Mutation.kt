package com.archesky.content.resolvers

import com.archesky.auth.library.graphql.CheckTokenQuery
import com.archesky.auth.library.service.TokenService
import com.archesky.content.repository.ContentRepository
import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.pynguins.content.service.MutationService
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class Mutation(private val mutationService: MutationService): GraphQLMutationResolver {
    fun createContent(content: String, environment: DataFetchingEnvironment): Content {
        return mutationService.createContent(content)
    }

    fun updateContent(id: String, content: String, environment: DataFetchingEnvironment): Content? {
        return mutationService.updateContent(id, content)
    }

    fun deleteContent(id: String, environment: DataFetchingEnvironment): Content? {
        return mutationService.deleteContent(id)
    }
}