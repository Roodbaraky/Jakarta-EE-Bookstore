package org.pluralsight;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static jakarta.ws.rs.core.MediaType.TEXT_PLAIN;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/api/books")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class BooksResource {

    @GET
    public Response getBooks() {
        List<Book> books = new ArrayList<>();

        if(books.isEmpty())
            return Response.noContent().build();

        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBook(@PathParam("id") Long id) {
        Book book = null;

        if (book == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(book).build();
    }

    @GET
    @Produces(TEXT_PLAIN)
    public Response countBooks() {
        Long nbOfBooks = 0L;

        if (nbOfBooks == 0)
            return Response.noContent().build();

        return Response.ok(nbOfBooks).build();
    }

    @POST
    public Response createBook(Book book) throws URISyntaxException {
        book.setId(1L);
        URI createdURI = new URI(book.getId().toString());
        return Response.created(createdURI).entity(book).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") Long id) {
        return Response.noContent().build();
    }
}
