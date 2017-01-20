package monadicctrl

import akka.http.scaladsl.marshalling._

trait AkkaHttpMarshallingSupport {

  implicit def futureControllerFlowMarshaller[A](implicit
    m: ToResponseMarshaller[A],
    em: ToResponseMarshaller[WebError]
  ): ToResponseMarshaller[FutureCtrlFlow[A]] =
    Marshaller(implicit ec => _.value.flatMap {
      case Right(a) => m(a)
      case Left(err) => em(err)
    })

  implicit val webErrorMarshaller: ToResponseMarshaller[WebError] =
    Marshaller { _ => _ => ??? }

}

object AkkaHttpMarshallingSupport extends AkkaHttpMarshallingSupport
