import cats.data.EitherT
import scala.concurrent.Future

package object monadicctrl {
  type CtrlFlow[A] = Either[WebError, A]
  type CtrlFlowT[F[_], B] = EitherT[F, WebError, B]
  type FutureCtrlFlow[B] = CtrlFlowT[Future, B]
}

